//
//  MLTitledMediaRow.swift
//  iosApp
//
//  Created by Nadine Cilliers on 15/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct MLTitledMediaRow: View {
    
    let rowTitle: String
    let media: [MediaItemUI]
    let onMediaItemClicked: (MediaItemUI) -> Void
    let onTitleClicked: () -> Void
    
    init(rowTitle: String, media: [MediaItemUI], onMediaItemClicked: @escaping (MediaItemUI) -> Void, onTitleClicked: @escaping () -> Void = {}) {
        self.rowTitle = rowTitle
        self.media = media
        self.onMediaItemClicked = onMediaItemClicked
        self.onTitleClicked = onTitleClicked
    }
    
    var body: some View {
        VStack{
            Text(rowTitle)
                .foregroundColor(.white)
                .customFont(.subtitle1)
                .frame(maxWidth: .infinity, alignment: .leading)
                .onTapGesture { onTitleClicked() }
            
            if(!media.isEmpty) {
                ScrollView(.horizontal) {
                    HStack(spacing: 20) {
                        if (!media.isEmpty) {
                            ForEach(media, id: \.self) { item in
                                MLMediaPoster(
                                    title: item.title,
                                    posterUrl: item.posterUrl
                                )
                                    .frame(width: 100, height: 170)
                                    .onTapGesture {
                                        onMediaItemClicked(item)
                                    }
                                    
                            }
                        }
                    }
                }
                .padding(12)
            } else {
                NavigationLink(value: MLRootDestinations.search) {
                    MLEmptyCollectionView()
                }
            }
        }
        .background(Color.background)
    }
}

struct MLTitledMediaRow_Previews: PreviewProvider {
    static var previews: some View {
        MLTitledMediaRow(
            rowTitle: "Related Movie Titles",
            media: [
                MediaItemUI(id: "1113", title: "Movie One", posterUrl: "https://search.yahoo.com/search?p=noster", bannerUrl: "http://www.bing.com/search?q=lacinia", categories: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie),
                MediaItemUI(id: "1113", title: "Movie One", posterUrl: "https://search.yahoo.com/search?p=noster", bannerUrl: "http://www.bing.com/search?q=lacinia", categories: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie),
                MediaItemUI(id: "1113", title: "Movie One", posterUrl: "https://search.yahoo.com/search?p=noster", bannerUrl: "http://www.bing.com/search?q=lacinia", categories: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie),
                MediaItemUI(id: "1113", title: "Movie One", posterUrl: "https://search.yahoo.com/search?p=noster", bannerUrl: "http://www.bing.com/search?q=lacinia", categories: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie),
                MediaItemUI(id: "1113", title: "Movie One", posterUrl: "https://search.yahoo.com/search?p=noster", bannerUrl: "http://www.bing.com/search?q=lacinia", categories: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie),
            ],
            onMediaItemClicked: {_ in}
        )
    }
}

struct MLTitledMediaRow_Empty_Collection_Previews: PreviewProvider {
    static var previews: some View {
        MLTitledMediaRow(
            rowTitle: "Collection with no media",
            media: [],
            onMediaItemClicked: {_ in}
        )
    }
}
