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
    
    let media: MediaItemUI
    
    var body: some View {
        ZStack {
            CachedAsyncImage(
                url: URL(string: media.bannerUrl)
            ) { image in
                image
                    .resizable()
                    .aspectRatio(contentMode: .fill)
            } placeholder: {
                ZStack {
                    LinearGradient(gradient: Gradient(colors: [.primaryVariant, .mlPrimary]), startPoint: .topTrailing, endPoint: .bottomLeading)
                    Text(media.title)
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
            media: MediaItemUI(id: 1113, title: "Movie One", isFavorited: false, posterUrl: "https://image.tmdb.org/t/p/original/wuMc08IPKEatf9rnMNXvIDxqP4W.jpg", bannerUrl: "https://image.tmdb.org/t/p/original/hziiv14OpD73u9gAak4XDDfBKa2.jpg", genreIds: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie)
        )
        .frame(width: 130.0, height: 90.0)
    }
}
