//
//  MLMediaPoster.swift
//  iosApp
//
//  Created by Nadine Cilliers on 15/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared
import CachedAsyncImage

struct MLMediaPoster: View {
    
    let title: String
    let posterUrl: String
    
    var body: some View {
        ZStack {
            CachedAsyncImage(
                url: URL(string: posterUrl)
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

struct MLMediaPoster_Previews: PreviewProvider {
    static var previews: some View {
        MLMediaPoster(
            title: "Movie One",
            posterUrl: "https://image.tmdb.org/t/p/original/wuMc08IPKEatf9rnMNXvIDxqP4W.jpg"
            )
            .frame(width: 100, height: 150)
    }
}
