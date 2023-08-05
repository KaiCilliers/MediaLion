//
//  MLMediaFavoriteListItem.swift
//  iosApp
//
//  Created by Nadine Cilliers on 15/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct MLMediaFavoriteListItem: View {

    let mediaItem: MediaItemUI
    let isFavorited: Bool
    var onFavoriteClick: (Bool) -> Void

    var body: some View {
        HStack(spacing: 0.0) {

            MLMediaBanner(
                title: mediaItem.title,
                bannerUrl: mediaItem.bannerUrl
            )
                .frame(width: 130.0, height: 90.0)
                .padding(.trailing, 16.0)
                
            Text(mediaItem.title)
                .multilineTextAlignment(.leading)
                .customFont(.h4)
                .foregroundColor(.white)
                .padding(.leading, 8)

            Spacer()

            Button{
                onFavoriteClick(!isFavorited)
            } label: {
                if (isFavorited) {
                    Image("heartFilledIcon")
                        .resizable()
                        .frame(width: 35.0, height: 30.0)
                        .padding(.horizontal, 16.0)
                } else {
                    Image("heartOutlineIcon")
                        .resizable()
                        .frame(width: 35.0, height: 30.0)
                        .padding(.horizontal, 16.0)
                }
            }
        }
        
        .background(Color.background)
        
    }
}

struct MLMediaFavoriteListItem_Previews: PreviewProvider {
    static var previews: some View {
        MLMediaFavoriteListItem(
            mediaItem: MediaItemUI(
                id: "123",
                title: "Movie One",
                posterUrl: "",
                bannerUrl: "https://image.tmdb.org/t/p/original/hziiv14OpD73u9gAak4XDDfBKa2.jpg",
                categories: [],
                overview: "",
                popularity: 0.0,
                voteAverage: 0.0,
                voteCount: 0,
                releaseYear: "1996",
                mediaType: MediaType.movie
            ),
            isFavorited: true,
            onFavoriteClick: { favorited in }
        )
    }
}
