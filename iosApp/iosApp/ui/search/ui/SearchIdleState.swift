//
//  SearchIdleState.swift
//  iosApp
//
//  Created by Nadine Cilliers on 15/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct SearchIdleState: View {
    @State var selectedFavouriteItem : Bool = false
    var body: some View {
        VStack{
            
          
            
            ScrollView(.vertical) {
                Text("Top Suggestions")
                    .foregroundColor(.white)
                    .customFont(.h2)
                    .padding(.top, 10)
                    .frame(maxWidth: .infinity,alignment: .leading)
                VStack(spacing: 30) {
                    ForEach(0...20, id: \.self) {value in
                        MLMediaFavoriteListItem(selectedFavouriteItem: $selectedFavouriteItem, onFavoritedClicked: { fav in selectedFavouriteItem = fav})
                            
                            
                    }
                }
            }
            .padding(.leading, 16)
        }
        
        .background(Color.background)
        
    }
}

struct SearchIdleState_Previews: PreviewProvider {
    static var previews: some View {
        SearchIdleState()
    }
}
