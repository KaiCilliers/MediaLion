//
//  MLMediaPoster.swift
//  iosApp
//
//  Created by Nadine Cilliers on 15/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct MLMediaPoster: View {
    var body: some View {
        ZStack {
            LinearGradient(gradient: Gradient(colors: [.primaryVariant, .mlPrimary]), startPoint: .topTrailing, endPoint: .bottomLeading)
            Text("HP")
                .customFont(.h2)
                .foregroundColor(Color.white)
        
        }
        
       
        .cornerRadius(6)
        
    }
}

struct MLMediaPoster_Previews: PreviewProvider {
    static var previews: some View {
        MLMediaPoster()
            .frame(width: 100, height: 150)
    }
}
