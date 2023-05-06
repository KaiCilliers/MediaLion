//
//  MLCollectionListItem.swift
//  iosApp
//
//  Created by Nadine Cilliers on 15/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct MLCollectionListItem: View {
    var body: some View {
        HStack{
            Image("placeholderReel")
                .resizable()
                .frame(width: 40, height: 40)
                .padding(.horizontal)
               
            Spacer()
                .frame(width: 10)
            Text("Title")
                .customFont(.h4)
            Spacer()
            Image(systemName: "checkmark")
                .resizable()
                .frame(width: 20, height: 20)
                .padding(.horizontal)
                
        }
    }
}

struct MLCollectionListItem_Previews: PreviewProvider {
    static var previews: some View {
        MLCollectionListItem()
    }
}
