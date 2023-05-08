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

    @ObservedObject var viewModel = SearchViewModel()
    
    var body: some View {
        
        VStack {
            
            MLSearchBar(
                text: viewModel.state.searchQuery,
                labelText: "Search for a show, movie or documentary",
                onSearchQueryTextChanged: { query in
                    viewModel.submitAction(action: SearchAction.SubmitSearchQuery(query: query))
                },
                onClearSearchText: {
                    viewModel.submitAction(action: SearchAction.ClearSearchText())
                }
            )
            
            switch(viewModel.state) {
                case let idle as SearchState.Idle:
                    SearchIdleState(
                        rowTitle: "Top Suggestions",
                        media: idle.suggestedMedia,
                        onMediaClicked: {_ in},
                        onFavoriteToggle: {_,_ in}
                    )
                case _ as SearchState.Results:
                    Text("results")
                case _ as SearchState.Loading:
                    Text("Loading...")
                case _ as SearchState.Empty:
                    Text("empty")
                default:
                Text("Default - \(viewModel.state.searchQuery ) ")
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
