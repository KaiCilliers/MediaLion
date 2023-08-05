//
//  MLTabItem.swift
//  iosApp
//
//  Created by Nadine Cilliers on 09/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct MLTabItem: View {
    
    let tabItem: TabItem
    var isSelected: Bool
    let onTabClicked: (TabItem) -> Void
    
    var body: some View {

        GeometryReader { geo in
            
            if isSelected {
                Rectangle()
                    .foregroundColor(.dialogOrange)
                    .frame(width: geo.size.width/2, height: 6)
                .padding(.leading, geo.size.width/4)
                
            }
            
            VStack (alignment: .center, spacing: 4){
                Image(tabItem.image)
                    .resizable()
                    .scaledToFit()
                    .frame(width: 20, height: 24)
                   
                Text(tabItem.title)
                    .customFont(.h1)
                    .foregroundColor(Color.primaryVariant)
            }
            .frame(width: geo.size.width, height: geo.size.height)
        }
        .tint(Color.primaryVariant)
        .onTapGesture {
            onTabClicked(tabItem)
        }
        
    }
}

struct MLTabItem_Previews: PreviewProvider {
    static var previews: some View {
        MLTabItem(
            tabItem: TabItem(image: "homeIcon", title: "Discovery"),
            isSelected: false,
            onTabClicked: {_ in}
        )
    }
}
