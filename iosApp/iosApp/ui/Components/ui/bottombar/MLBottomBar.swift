////
////  bottomBar.swift
////  iosApp
////
////  Created by Nadine Cilliers on 02/05/2023.
////  Copyright © 2023 orgName. All rights reserved.
////
//
//import SwiftUI
//import shared
//
//struct MLBottomBar: View {
//    
//    @Binding var selectedTab : MLTabDestinations
//    
//    var body: some View {
//        
//                HStack{
//                    
//                    let isDiscoverySelected: Bool = {
//                        if selectedTab == .discovery {
//                            return true
//                        } else {
//                            return false
//                        }
//                    }()
//                    
//                    let isCollectionSelected: Bool = {
//                        if selectedTab == .collection {
//                            return true
//                        } else {
//                            return false
//                        }
//                    }()
//                    
//                    MLTabItem(
//                        tabItem: TabItem(image: "homeIcon", title: StringRes.bottomBarHome),
//                        isSelected: isDiscoverySelected,
//                        onTabClicked: { tab in selectedTab = .discovery }
//                    )
//                    
//                    MLTabItem(
//                        tabItem: TabItem(image: "slideOrangeCogIcon", title: StringRes.bottomBarCollection),
//                        isSelected: isCollectionSelected,
//                        onTabClicked: { tab in selectedTab = .collection }
//                    )
//                }
//                .frame(height: 82)
//                .background(LinearGradient(gradient: Gradient(colors: [.mlPrimary, .primaryVariantBlue]), startPoint: .top, endPoint: .bottom))
//                .edgesIgnoringSafeArea(.all)
//            }
//        }
//    
//
//
//enum MLTabDestinations: Int {
//    case discovery = 0
//    case collection = 1
//}
//
//enum MLRootDestinations: Int, Hashable {
//    case search
//}
//
//struct bottomBar_Previews: PreviewProvider {
//    static var previews: some View {
//        StatefulPreviewWrapper(MLTabDestinations.discovery) {
//            MLBottomBar(selectedTab: $0)
//        }
//    }
//}
