//
//  SearchScreen.swift
//  iosApp
//
//  Created by Nadine Cilliers on 15/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared
typealias AColor = SwiftUI.Color

struct SearchScreen: View {
    
    @Environment(\.presentationMode) var presentationMode
    @StateObject private var viewModel = SearchViewModel()
    @State private var showAboutDialog: Bool = false
    @State private var selectedMediaItem: MediaItemUI? = nil
    @State private var showDetailSheet = false
    
    init() {
        NapierProxyKt.debugBuild()
    }
    
    var body: some View {
        
        let screenBlurAmount: Float = {
            if showAboutDialog {
                return 4
            } else {
                return 0
            }
        }()
        
        ZStack {
            VStack (alignment: .center, spacing: 0){
                
                HStack{
                    
                    Button {
                        self.presentationMode.wrappedValue.dismiss()
                    } label: {
                        Image("backArrowIcon")
                            .resizable()
                            .frame(width: 27, height: 30)
                    }
                    
                    Spacer()
                    
                    Button {
                        showAboutDialog = true
                    } label: {
                        Image("aboutIcon")
                            .resizable()
                            .frame(width: 30, height: 30)
                    }
                    
                }
                .padding()
                .background(Color.background)
                
                MLSearchBar(
                    text: viewModel.state.searchQuery,
                    labelText: StringRes.emptySearch,
                    onSearchQueryTextChanged: { query in
                        viewModel.submitAction(action: SearchAction.SubmitSearchQuery(query: query))
                    },
                    onClearSearchText: {
                        viewModel.submitAction(action: SearchAction.ClearSearchText())
                    }
                )
                
                switch(viewModel.state) {
                    
                case let idleState as SearchState.Idle:
                    SearchIdleState(
                        rowTitle: StringRes.topSuggestions,
                        media: idleState.suggestedMedia,
                        onMediaClicked: { item in
                            selectedMediaItem = item
                            showDetailSheet = true
                        },
                        onFavoriteToggle: { item, favorited in
                            if(favorited) {
                                viewModel.submitAction(
                                    action: SearchAction.AddToFavorites(
                                        mediaId: item.id,
                                        mediaType: item.mediaType
                                    )
                                )
                            } else {
                                viewModel.submitAction(
                                    action: SearchAction.RemoveFromFavorites(
                                        movieId: item.id,
                                        mediaType: item.mediaType
                                    )
                                )
                            }
                        }
                    )
                    
                case let resultState as SearchState.Results:
                    MLTitledMediaGrid(
                        gridTitle: StringRes.topResults,
                        media: resultState.searchResults,
                        suggestedMedia: resultState.relatedTitles,
                        onMediaItemClicked: { value in
                            // show media detail sheet
                        }
                    )
                    
                case _ as SearchState.Loading:
                    ProgressView("Searching for media...")
                    
                case _ as SearchState.Empty:
                    SearchEmptyState()
                    
                default:
                    fatalError("Unreachable state! \(viewModel.state)")
                }
            }
            .blur(radius: CGFloat(screenBlurAmount))
            .disabled(showAboutDialog)
            
            if showAboutDialog {
                MLAboutDialog(
                    onCloseAction: {
                        showAboutDialog = false
                    }
                )
            }
        }
        .onAppear {
            print("IOS - starting to observe viewModel")
            viewModel.observe()
        }
        .onDisappear {
            print("IOS - disposing viewModel")
            viewModel.dispose()
        }
        .sheet(isPresented: $showDetailSheet) {
            if let itemToPreview = selectedMediaItem {
                DetailPreviewSheet(
                    mediaItem: itemToPreview,
                    onCloseClick: {
                        showDetailSheet = false
                    },
                    onMyCollectionClick: { item in
                        // todo show my collection dialog
                    }
                )
                .presentationDetents([.medium, .fraction(0.4)])
                .presentationDragIndicator(.hidden)
            } else {
//                fatalError("item to show was nil!")
            }
        }
        
    }
    
    struct SearchScreen_Previews: PreviewProvider {
        static var previews: some View {
            SearchScreen()
        }
    }
}
