//
//  LoadingScreen.swift
//  iosApp
//
//  Created by Nadine Cilliers on 10/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct MLSearchLoading: View {
    var body: some View {
        ZStack{
            ProgressView("Searching for media...")
                .progressViewStyle(CircularProgressViewStyle(tint: Color.dialogOrange))
                .customFont(.subtitle1)
                .foregroundColor(.dialogOrange)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Color.background)
        
    }
}

struct MLSearchLoading_Previews: PreviewProvider {
    static var previews: some View {
        MLSearchLoading()
    }
}
