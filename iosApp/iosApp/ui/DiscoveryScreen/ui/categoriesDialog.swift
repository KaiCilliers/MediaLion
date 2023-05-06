//
//  categoriesDialog.swift
//  iosApp
//
//  Created by Nadine Cilliers on 02/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

@available(iOS 15.0, *)
struct categoriesDialog: View {
    @State var isActive : Bool = false
    var body: some View {
        ZStack{
            VStack{
                Button{
                    isActive = true
                } label: {
                    Text("Categories")
                        .foregroundColor(.mlLightBlue)
                        .background(Color.background)
                        .customFont(.h3)
                }
            }
            .padding()
            
            if isActive {
                customCategoriesDialog(isActive: .constant(true), title: "Categories", action: {})
            }
        }
    }
}

@available(iOS 15.0, *)
struct categoriesDialog_Previews: PreviewProvider {
    static var previews: some View {
        categoriesDialog()
    }
}