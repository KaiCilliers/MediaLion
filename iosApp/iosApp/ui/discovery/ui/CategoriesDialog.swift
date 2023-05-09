//
//  categoriesDialog.swift
//  iosApp
//
//  Created by Nadine Cilliers on 02/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct CategoriesDialog: View {
    @State var isActive : Bool = false
    var body: some View {
        ZStack{
            VStack{
                Button{
                    isActive = true
                } label: {
                    Text(StringRes.categories)
                        .foregroundColor(.mlLightBlue)
                        .background(Color.background)
                        .customFont(.h3)
                }
            }
            .padding()
            
            if isActive {
                CustomCategoriesDialog(
                    title: StringRes.categories,
                    onClose: {}
                )
            }
        }
    }
}

struct categoriesDialog_Previews: PreviewProvider {
    static var previews: some View {
        CategoriesDialog()
    }
}
