//
//  LimitedDetailScreen.swift
//  iosApp
//
//  Created by Nadine Cilliers on 05/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct LimitedDetailScreen: View {
    let item: String
    @State private var selection = 2
    
    var body: some View {
        VStack {
            Text(item)
            NavigationLink(destination: CompleteDetailScreen(item: item)) {
                Text("Show more details")
            }
            TabView(selection: $selection) {
                RedView()
                    .tabItem {
                        Image(systemName: "star")
                        Text("Red Tab")
                    }.tag(1)
                GreenView()
                    .tabItem {
                        Image(systemName: "tv.fill")
                        Text("Green Tab")
                    }.tag(2)
                BlueView()
                    .tabItem {
                        Image(systemName: "car.fill")
                        Text("Blue Tab")
                    }.tag(3)
            }
        }
    }
}

struct RedView: View {
    var body: some View {
        Color.red
    }
}
struct BlueView: View {
    var body: some View {
        Color.blue
    }
}
struct GreenView: View {
    var body: some View {
        Color.green
    }
}

struct LimitedDetailScreen_Previews: PreviewProvider {
    static var previews: some View {
        LimitedDetailScreen(item: "example")
    }
}
