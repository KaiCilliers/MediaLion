//
//  SearchEmptyState.swift
//  iosApp
//
//  Created by Nadine Cilliers on 15/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct SearchEmptyState: View {
    var body: some View {
        ZStack{
            VStack{
                Text("We could not find what you are looking for.")
                    .frame(maxWidth: .infinity, alignment: .topLeading)
                    .customFont(.subtitle1)
                    .padding(.bottom, 1)
                
               
                
                Text("Try searching for another movie, show, actor, director, or genre")
                    .frame(maxWidth: .infinity, alignment: .topLeading)
                    .customFont(.h1)
                Spacer()
                
            }
            .foregroundColor(.white)
            .padding(.leading)
            .padding(.top)
          
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Color.background)
        
        
    }
}

struct SearchEmptyState_Previews: PreviewProvider {
    static var previews: some View {
        SearchEmptyState()
    }
}
