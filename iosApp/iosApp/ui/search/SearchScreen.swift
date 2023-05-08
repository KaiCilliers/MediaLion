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
    
    @State var screen = 0
    @StateObject var blueUser = User(color: Color.primaryVariant)
    @StateObject var greenUser = User(color: Color.green)
    @ObservedObject var viewModel = SearchViewModel()
    
    var body: some View {
        
        VStack {
            switch(viewModel.state) {
            case let idle as SearchState.Idle:
                Button(action: {
                    viewModel.submitAction(action: SearchAction.SubmitSearchQuery(query: "k"))
                    viewModel.submitAction(action: SearchAction.SubmitSearchQuery(query: "ka"))
                    viewModel.submitAction(action: SearchAction.SubmitSearchQuery(query: "kai"))
                    viewModel.submitAction(action: SearchAction.AddToFavorites(mediaId: 758323, mediaType: MediaType.movie))
                }) {
                    let s = (idle as SearchState.Idle).suggestedMedia
                    Text("IDLE - \(idle.searchQuery) + \(s.count)")
                }
            case let results as SearchState.Results:
                Text("results! \(results.searchResults.first!.title)")
            case _ as SearchState.Loading:
                Text("Loading...")
            case _ as SearchState.Empty:
                Text("empty")
            default:
                Text("Default - \(viewModel.state?.searchQuery ?? "nil") ")
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
