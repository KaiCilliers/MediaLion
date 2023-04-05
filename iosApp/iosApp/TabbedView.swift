//
//  LimitedDetailScreen.swift
//  iosApp
//
//  Created by Nadine Cilliers on 05/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct TabbedView: View {
    @State private var selection = 2
    @StateObject var redUser = User(color: Color.red)
    @StateObject var greenUser = User(color: Color.green)
    @StateObject var blueUser = User(color: Color.blue)
    
    var body: some View {
        TabView(selection: $selection) {
            
            NavigationView {
                RedView()
            }
            .tabItem {
                Image(systemName: "star")
                Text("Red Tab")
            }
            .tag(1)
            .environmentObject(redUser)
            
            NavigationView {
                GreenView()
            }
            .tabItem {
                Image(systemName: "tv.fill")
                Text("Green Tab")
            }
            .tag(2)
            .environmentObject(greenUser)
            
            NavigationView {
                BlueView()
            }
            .tabItem {
                Image(systemName: "car.fill")
                Text("Blue Tab")
            }
            .tag(3)
            .environmentObject(blueUser)
        }
        .navigationTitle("Tabbed View")
        .navigationBarTitleDisplayMode(.large)
    }
}

struct LimitedDetailScreen_Previews: PreviewProvider {
    static var previews: some View {
        TabbedView()
    }
}

struct RedView: View {
    @EnvironmentObject var user: User
    
    var body: some View {
        ZStack {
            NavigationLink(destination: Dest(value: "red")) {
                user.color
            }
            Button("User score = \(user.score) (tap to increase value)") { self.user.score += 1 }
                .padding(20)
                .background(user.color.colorInvert())
        }
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
struct GreenView: View {
    @EnvironmentObject var user: User
    
    var body: some View {
        ZStack {
            NavigationLink(destination: Dest(value: "green")) {
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
