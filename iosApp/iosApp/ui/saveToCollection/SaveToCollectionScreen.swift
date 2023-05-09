//
//  SaveToCollectionScreen.swift
//  iosApp
//
//  Created by Nadine Cilliers on 15/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

@available(iOS 15.0, *)
struct SaveToCollectionScreen: View {
    
    @State var isActive: Bool = false
    
    var body: some View {
        ZStack{
            VStack{
                Button{
                    isActive = true
                    
                } label: {
                    Image("addToListIcon")
                }
            }
            .padding()
            
            if isActive {
                CustomCollectionDialog(isActive: .constant(true), title: StringRes.saveToListTitle, action: {}, buttoTitle: "Done")
            }
        }
    }
}

@available(iOS 15.0, *)
struct SaveToCollectionScreen_Previews: PreviewProvider {
    static var previews: some View {
        SaveToCollectionScreen()
    }
}
