//
//  bottomBar.swift
//  iosApp
//
//  Created by Nadine Cilliers on 02/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct MLBottomBar: View {
    
    @State var selectedTab : Tabs = .home
    let onTabSelected: (TabItem) -> Void
    
    init(onTabSelected: @escaping (TabItem) -> Void) {
        self.onTabSelected = onTabSelected
        onTabSelected(TabItem(image: "homeIcon", title: "Discovery"))
    }
    
    var body: some View {
        HStack{
            
            let isHomeSelected: Bool = {
                if selectedTab == .home {
                    return true
                } else {
                    return false
                }
            }()
            
            let isCollectionSelected: Bool = {
                if selectedTab == .collection {
                    return true
                } else {
                    return false
                }
            }()
            
            MLTabItem(
                tabItem: TabItem(image: "homeIcon", title: "Discovery"),
                isSelected: isHomeSelected,
                onTabClicked: { tab in
                    selectedTab = .home
                    onTabSelected(tab)
                }
            )
            
            MLTabItem(
                tabItem: TabItem(image: "slideOrangeCogIcon", title: "My Collection"),
                isSelected: isCollectionSelected,
                onTabClicked: { tab in
                    selectedTab = .collection
                    onTabSelected(tab)
                }
            )
        }
        .frame(height: 82)
        .background(LinearGradient(gradient: Gradient(colors: [.mlPrimary, .primaryVariantBlue]), startPoint: .top, endPoint: .bottom))
        .edgesIgnoringSafeArea(.all)
    }
}

extension MLBottomBar {
    enum Tabs: Int {
        case home = 0
        case collection = 1
    }
}

struct bottomBar_Previews: PreviewProvider {
    static var previews: some View {
        MLBottomBar(onTabSelected: {_ in})
    }
}
