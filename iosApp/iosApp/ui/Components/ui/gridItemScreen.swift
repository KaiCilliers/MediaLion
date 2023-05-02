//
//  gridItemScreen.swift
//  iosApp
//
//  Created by Nadine Cilliers on 02/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

@available(iOS 16.0, *)
struct gridItemScreen: View {
    @State var selectedTab : Tabs = .home
    
    var body: some View {
        VStack (alignment: .center, spacing: 0) {
            HStack{
                Image("backArrowIcon")
                    .resizable()
                    .frame(width: 30, height: 30)
                    .padding()
                Spacer()
                Image("aboutIcon")
                    .resizable()
                    .frame(width: 30, height: 30)
                    .padding()
            }.background(Color.background)
            MLTitledMediaGrid(title: "Horror")
            
            bottomBar(selectedTab: $selectedTab)
        }
    }
}

@available(iOS 16.0, *)
struct gridItemScreen_Previews: PreviewProvider {
    static var previews: some View {
        gridItemScreen()
    }
}
