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
    @State private var mediaPreviewSheet: PreviewMedia = PreviewMedia(media: nil,sheetVisible: false)
    @State private var showCollectionDialog = false
    @State private var selectedMedia: MediaItemUI? = nil
    
    var body: some View {
        
        let screenBlurAmount: Float = {
            if (showAboutDialog || mediaPreviewSheet.sheetVisible || showCollectionDialog) {
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
                            mediaPreviewSheet.showSheet(media: item)
                            selectedMedia = item
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
                        onMediaItemClicked: { media in
                            mediaPreviewSheet.showSheet(media: media)
                            selectedMedia = media
                        }
                    )
                    
                case _ as SearchState.Loading:
                    LoadingScreen()
                    
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
            
            if showCollectionDialog {
                MLCollectionsDialog(
                    onDismiss: { showCollectionDialog = false },
                    collections: viewModel.collectionState.compactMap({ $0 as? CollectionWithMedia }).map({ singleCollection in
                      
                        let selectedMedia = selectedMedia
                        var checked = false
                        
                        if (selectedMedia != nil) {
                            let collectionsWithMediaIds = singleCollection.contents.map { mediaItem in
                                return mediaItem.id
                            }
                            
                            checked = collectionsWithMediaIds.contains(selectedMedia!.id)
                        }
                        
                        return CollectionItem(name: singleCollection.name as! String, checked: checked)
                        
                    }),
                    onAddToCollection: { collectionName in
                        if let selectedMediaItem = selectedMedia {
                            viewModel.submitAction(
                                action: SearchAction.AddToCollection(
                                    collectionName: collectionName,
                                    mediaId: selectedMediaItem.id,
                                    mediaType: selectedMediaItem.mediaType)
                            )
                        }
                    },
                    onRemoveFromCollection: { collectionName in
                        if let selectedMediaItem = selectedMedia {
                            viewModel.submitAction(
                                action: SearchAction.RemoveFromCollection(
                                    collectionName: collectionName,
                                    mediaId:selectedMediaItem.id,
                                    mediaType: selectedMediaItem.mediaType
                                )
                            )
                        }
                    },
                    onCreateNewCollection: { collectionName in
                        viewModel.submitAction(action: SearchAction.CreateCollection(collectionName: collectionName))
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
        .sheet(isPresented: $mediaPreviewSheet.sheetVisible) {
            if let itemToPreview = mediaPreviewSheet.media {
                DetailPreviewSheet(
                    mediaItem: itemToPreview,
                    onCloseClick: {
                        mediaPreviewSheet.hideSheet()
                    },
                    onMyCollectionClick: { item in
                        showCollectionDialog = true
                        mediaPreviewSheet.hideSheet()
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

struct PreviewMedia {
    var media: MediaItemUI? = nil
    var sheetVisible: Bool = false
    
    mutating func showSheet(media: MediaItemUI) {
        self.media = media
        self.sheetVisible = true
    }
    
    mutating func hideSheet() {
        self.media = nil
        self.sheetVisible = false
    }
}
