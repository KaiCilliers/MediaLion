//
//  discoveryScreen.swift
//  iosApp
//
//  Created by Nadine Cilliers on 02/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

@available(iOS 15.0, *)
@available(iOS 16.0, *)
struct discoveryScreen: View {
    
    @State var selectedTab : Tabs = .home
    @State var isActive : Bool = false
    
    var body: some View {
        ZStack{
            VStack (alignment: .center, spacing: 0){
                HStack{
                    Image("searchIcon")
                        .resizable()
                        .frame(width: 27, height: 30)
                    
                    Spacer()
                    Image("logoIcon")
                        .resizable()
                        .frame(width: 100, height: 80)
                    Spacer()
                    Image("aboutIcon")
                        .resizable()
                        .frame(width: 30, height: 30)
                    
                }
                .padding()
                .background(Color.background)
                
                discoveryFilterItems( action: {filter in
                    if filter == .categories {
                        isActive = true
                    }
                       
                }
                )
                
                ScrollView {
                    VStack (alignment: .center, spacing: 0){
//                        MLTitledMediaRow()
//                        MLTitledMediaRow()
//                        MLTitledMediaRow()
//                        MLTitledMediaRow()
//                        MLTitledMediaRow()
//                        MLTitledMediaRow()
                    }
                }
                
                bottomBar(selectedTab: $selectedTab)
                
            }.background(Color.background)
        }
        if isActive {
            customCategoriesDialog(isActive: .constant(true), title: "Categories", action: {isActive = false})
        }
    }
}

@available(iOS 15.0, *)
@available(iOS 16.0, *)
struct discoveryScreen_Previews: PreviewProvider {
    static var previews: some View {
        discoveryScreen()
    }
}
