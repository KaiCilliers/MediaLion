//
//  MLMediaFavoriteListItem.swift
//  iosApp
//
//  Created by Nadine Cilliers on 15/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared



struct MLMediaFavoriteListItem: View {
    
    @Binding var selectedFavouriteItem : Bool
    let onFavoritedClicked: (Bool) -> Void
    
    var body: some View {
        HStack(spacing: 0.0) {
            MLMediaPoster()
                .frame(width: 130.0, height: 90.0)
                .padding(.trailing, 16.0)
                
            Text("Title")
                .multilineTextAlignment(.leading)
                .customFont(.h4)
                .foregroundColor(.white)
            Spacer()
            
            Button {
                selectedFavouriteItem = !selectedFavouriteItem
            } label: {
                if selectedFavouriteItem == true {
                    Image("heartOutlineIcon")
                        .resizable()
                        .frame(width: 35.0, height: 30.0)
                        .padding(.horizontal, 16.0)
                } else {
                    Image("heartFilledIcon")
                        .resizable()
                        .frame(width: 35.0, height: 30.0)
                        .padding(.horizontal, 16.0)
                }
            }
            
        }
        
        .background(Color.background)
        
    }
}

struct MLMediaFavoriteListItem_Previews: PreviewProvider {
    static var previews: some View {
        StatefulPreviewWrapper(true) {
            MLMediaFavoriteListItem(selectedFavouriteItem: $0, onFavoritedClicked: {_ in})
        }
    }
}
