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
    
    let media: MediaItemUI?
    
    var body: some View {
        ZStack {
            if (media != nil) {
                CachedAsyncImage(
                    url: URL(string: media!.posterUrl)
                ) { image in
                    image
                        .resizable()
                        .aspectRatio(contentMode: .fill)
                } placeholder: {
                    ZStack {
                        LinearGradient(gradient: Gradient(colors: [.primaryVariant, .mlPrimary]), startPoint: .topTrailing, endPoint: .bottomLeading)
                        Text(media!.title)
                            .customFont(.h1)
                            .foregroundColor(Color.white)
                    }
                }
            } else {
                ZStack {
                    LinearGradient(gradient: Gradient(colors: [.primaryVariant, .mlPrimary]), startPoint: .topTrailing, endPoint: .bottomLeading)
                    Text(StringRes.textPlaceholder)
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
            media: MediaItemUI(id: 1113, title: "Movie One", isFavorited: false, posterUrl: "https://image.tmdb.org/t/p/original/wuMc08IPKEatf9rnMNXvIDxqP4W.jpg", bannerUrl: "https://image.tmdb.org/t/p/original/hziiv14OpD73u9gAak4XDDfBKa2.jpg", genreIds: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie)
        )
            .frame(width: 100, height: 150)
    }
}
