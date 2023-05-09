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
    @ObservedObject var viewModel = SearchViewModel()
    @State var showAboutDialog: Bool = false
    
    init() {
        NapierProxyKt.debugBuild()
    }
    
    var body: some View {
        
        var screenBlurAmount: Float = {
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
                        onMediaClicked: {_ in},
                        onFavoriteToggle: {_,_ in}
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
                    Text("Placeholder - this state should not be reached")
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
            viewModel.observe()
        }
        .onDisappear {
            viewModel.dispose()
        }
        
    }
    
    struct SearchScreen_Previews: PreviewProvider {
        static var previews: some View {
            SearchScreen()
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    // Test data
    
    class User: ObservableObject {
        var color: AColor
        @Published var score = 0
        
        init(color: AColor, score: Int = 0) {
            self.color = color
            self.score = score
        }
    }
    
    struct BlueView: View {
        @EnvironmentObject var user: User
        
        var body: some View {
            ZStack {
                NavigationLink(destination: Dest(value: "blue")) {
                    user.color
                }
                Button("User score = \(user.score) (tap to increase value)") { self.user.score += 1 }
                    .padding(20)
                    .background(Color.white)
            }
        }
    }
    
    struct Dest: View {
        var value: String
        @EnvironmentObject var user: User
        
        var body: some View {
            Text("I got the score: \(user.score)")
        }
    }
}
