//
//  collectionScreen.swift
//  iosApp
//
//  Created by Nadine Cilliers on 02/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct CollectionsScreen: View {
    
    var body: some View {
        ZStack{
        VStack (alignment: .center, spacing: 0){
                HStack{
                    Image("searchIcon")
                        .resizable()
                        .frame(width: 27, height: 30)
                    Spacer()
                    Image("aboutIcon")
                        .resizable()
                        .frame(width: 30, height: 30)
                }
                .padding()
                .background(Color.background)
                
                
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
            }
       
        }
        .background(Color.background)
        
    }
}

@available(iOS 16.0, *)
struct collectionScreen_Previews: PreviewProvider {
    static var previews: some View {
        CollectionsScreen()
    }
}
