//
//  SearchIdleState.swift
//  iosApp
//
//  Created by Nadine Cilliers on 15/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct SearchIdleState: View {

    var rowTitle: String
    var media: [MediaItemUI]
    var onMediaClicked: (MediaItemUI) -> Void
    var onFavoriteToggle: (MediaItemUI, Bool) -> Void

    var body: some View {
        VStack{
            ScrollView(.vertical) {
                Text(rowTitle)
                    .foregroundColor(.white)
                    .customFont(.h2)
                    .padding(.top, 16)
                    .frame(maxWidth: .infinity,alignment: .leading)
                VStack(spacing: 30) {
                    ForEach(media, id: \.self) {item in
                        Button {
                            onMediaClicked(item)
                        } label: {
                            MLMediaFavoriteListItem(
                                mediaItem: item, onFavoriteClick: { favorited in
                                    onFavoriteToggle(item, favorited)
                                }
                            )
                        }
                    }
                }
            }
            .padding(.leading, 16)
        }
        
        .background(Color.background)
        
    }
}

struct SearchIdleState_Previews: PreviewProvider {
    static var previews: some View {
        SearchIdleState(
            rowTitle: "Top Suggestions",
            media: [
                MediaItemUI(id: 1113, title: "Movie One", isFavorited: false, posterUrl: "https://search.yahoo.com/search?p=noster", bannerUrl: "http://www.bing.com/search?q=lacinia", genreIds: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie),
                MediaItemUI(id: 6664, title: "Movie Two", isFavorited: false, posterUrl: "https://search.yahoo.com/search?p=noster", bannerUrl: "http://www.bing.com/search?q=lacinia", genreIds: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie),
                MediaItemUI(id: 264, title: "Movie Three", isFavorited: true, posterUrl: "https://search.yahoo.com/search?p=noster", bannerUrl: "http://www.bing.com/search?q=lacinia", genreIds: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie),

            ],
            onMediaClicked: {_ in},
            onFavoriteToggle: {_,_ in}
        )
    }
}
