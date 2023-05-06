//
//  SearchScreen.swift
//  iosApp
//
//  Created by Nadine Cilliers on 15/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct SearchScreen: View {

    @State var screen = 0
    @StateObject var blueUser = User(color: Color.blue)
    @StateObject var greenUser = User(color: Color.green)
    
    var body: some View {
        TabView(selection: $screen) {
            NavigationStack {
                BlueView()
            }
            .tabItem {
                Image(systemName: "star")
                Text("Discovery")
            }
            .tag(0)
            .environmentObject(blueUser)
            
            NavigationStack {
                BlueView()
            }
            .tabItem {
                Image(systemName: "tv.fill")
                Text("Collections")
            }
            .tag(1)
            .environmentObject(greenUser)
        }
    }
}

struct SearchScreen_Previews: PreviewProvider {
    static var previews: some View {
        SearchScreen()
    }
}

// Test data

class User: ObservableObject {
    var color: Color
    @Published var score = 0
    
    init(color: Color, score: Int = 0) {
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
