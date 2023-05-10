//
//  MLCollectionListItem.swift
//  iosApp
//
//  Created by Nadine Cilliers on 15/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct MLCollectionListItem: View {
    
    let collectionName: String
    var checked: Bool
    let onCollectionClicked: (Bool) -> Void
    
    var body: some View {
        HStack{
            Image("placeholderReel")
                .resizable()
                .frame(width: 40, height: 40)
                .padding(.horizontal)
               
            Spacer()
                .frame(width: 10)
            Text(collectionName)
                .customFont(.h4)
            Spacer()
            if (checked) {
                Image(systemName: "checkmark")
                    .resizable()
                    .frame(width: 20, height: 20)
                    .padding(.horizontal)
            }
        }
        .contentShape(Rectangle())
        .onTapGesture {
            onCollectionClicked(!checked)
        }
    }
}

struct MLCollectionListItem_Previews: PreviewProvider {
    static var previews: some View {
        MLCollectionListItem(
            collectionName: "Favorites",
            checked: true,
            onCollectionClicked: { _ in}
        )
    }
}
