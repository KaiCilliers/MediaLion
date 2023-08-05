//
//  MLMediaBanner.swift
//  iosApp
//
//  Created by Nadine Cilliers on 09/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared
import CachedAsyncImage

struct MLMediaBanner: View {
    
    let title: String
    let bannerUrl: String
    
    var body: some View {
        ZStack {
            CachedAsyncImage(
                url: URL(string: bannerUrl)
            ) { image in
                image
                    .resizable()
                    .aspectRatio(contentMode: .fill)
            } placeholder: {
                ZStack {
                    LinearGradient(gradient: Gradient(colors: [.primaryVariant, .mlPrimary]), startPoint: .topTrailing, endPoint: .bottomLeading)
                    Text(title)
                        .customFont(.h1)
                        .foregroundColor(Color.white)
                }
            }
        }
        .cornerRadius(6)
        
    }
}

struct MLMediaBanner_Previews: PreviewProvider {
    static var previews: some View {
        MLMediaBanner(
            title: "Movie One",
            bannerUrl: "https://image.tmdb.org/t/p/original/hziiv14OpD73u9gAak4XDDfBKa2.jpg"
            )
        .frame(width: 130.0, height: 90.0)
    }
}
