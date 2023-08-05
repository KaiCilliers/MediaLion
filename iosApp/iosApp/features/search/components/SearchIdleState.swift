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

    let rowTitle: String
    let mediaWithFavorites: [(MediaItemUI, Bool)]
    var onMediaClicked: (MediaItemUI) -> Void
    var onFavoriteToggle: (MediaItemUI, Bool) -> Void

    var body: some View {
        VStack{
            
            let media = mediaWithFavorites.map { (item,_) in item }
            let favorited = mediaWithFavorites.map { (_, favorited) in favorited }
            
            ScrollView(.vertical) {
                Text(rowTitle)
                    .foregroundColor(.white)
                    .customFont(.h2)
                    .padding(.top, 16)
                    .frame(maxWidth: .infinity,alignment: .leading)
                VStack(spacing: 30) {
                    ForEach(media, id: \.self) {item in
                        MLMediaFavoriteListItem(
                            mediaItem: item,
                            isFavorited: favorited[media.firstIndex(of: item)!],
                            onFavoriteClick: { favorited in
                                onFavoriteToggle(item, favorited)
                            }
                        )
                        // TODO convert this into a .clickable View extension
                        .containerShape(Rectangle())
                        .onTapGesture {
                            onMediaClicked(item)
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
            mediaWithFavorites: [
                (
                    MediaItemUI(id: "1113", title: "Movie One", posterUrl: "https://search.yahoo.com/search?p=noster", bannerUrl: "http://www.bing.com/search?q=lacinia", categories: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie),
                    true
                ),
                (
                    MediaItemUI(id: "11123", title: "Movie Two", posterUrl: "https://search.yahoo.com/search?p=noster", bannerUrl: "http://www.bing.com/search?q=lacinia", categories: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie),
                    true
                ),
                (
                    MediaItemUI(id: "11313", title: "Movie Three", posterUrl: "https://search.yahoo.com/search?p=noster", bannerUrl: "http://www.bing.com/search?q=lacinia", categories: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie),
                    false
                ),
                (
                    MediaItemUI(id: "11413", title: "Movie Four", posterUrl: "https://search.yahoo.com/search?p=noster", bannerUrl: "http://www.bing.com/search?q=lacinia", categories: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie),
                    true
                ),
            ],
            onMediaClicked: {_ in},
            onFavoriteToggle: {_,_ in}
        )
    }
}
