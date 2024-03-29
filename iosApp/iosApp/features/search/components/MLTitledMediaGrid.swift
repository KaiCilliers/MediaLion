//
//  MLTitledMediaGrid.swift
//  iosApp
//
//  Created by Nadine Cilliers on 15/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct MLTitledMediaGrid: View {
    
    let gridTitle: String
    let media: [MediaItemUI]
    let suggestedMedia: [TitledMedia]
    let onMediaItemClicked: (MediaItemUI) -> Void
    
    private let columns = [GridItem(.flexible()), GridItem(.flexible()), GridItem(.flexible())]
    
    var body: some View {
        
        ZStack{
            ScrollView{
                
                Text(gridTitle)
                    .foregroundColor(.white)
                    .customFont(.h2)
                    .padding(.leading, 16)
                    .padding(.top, 16)
                    .frame(maxWidth: .infinity, alignment: .leading)
                
                LazyVGrid(columns: columns, spacing: 16) {
                    ForEach(media, id: \.self) { item in
                        MLMediaPoster(
                            title: item.title,
                            posterUrl: item.posterUrl
                        )
                            .frame(width: 100,height: 170)
                            .onTapGesture {
                                onMediaItemClicked(item)
                            }
                        
                    }
                }
                
                LazyVStack {
                    ForEach(suggestedMedia, id: \.self) { titledMedia in
                        if (!titledMedia.content.isEmpty) {
                            MLTitledMediaRow(
                                rowTitle: titledMedia.title,
                                media: titledMedia.content,
                                onMediaItemClicked: { media in onMediaItemClicked(media)
                                }
                            )
                        }
                    }
                }
            }
        }
            .background(Color.background)
            .edgesIgnoringSafeArea(.all)
    }
}

struct MLTitledMediaGrid_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            MLTitledMediaGrid(
                gridTitle: "Top Results",
                media: [
                    MediaItemUI(id: "1113", title: "Movie One", posterUrl: "https://search.yahoo.com/search?p=noster", bannerUrl: "http://www.bing.com/search?q=lacinia", categories: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie),
                    MediaItemUI(id: "1114", title: "Movie One", posterUrl: "https://search.yahoo.com/search?p=noster", bannerUrl: "http://www.bing.com/search?q=lacinia", categories: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie),
                    MediaItemUI(id: "1115", title: "Movie One", posterUrl: "https://search.yahoo.com/search?p=noster", bannerUrl: "http://www.bing.com/search?q=lacinia", categories: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie),
                    MediaItemUI(id: "1116", title: "Movie One", posterUrl: "https://search.yahoo.com/search?p=noster", bannerUrl: "http://www.bing.com/search?q=lacinia", categories: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie),
                    MediaItemUI(id: "1117", title: "Movie One", posterUrl: "https://search.yahoo.com/search?p=noster", bannerUrl: "http://www.bing.com/search?q=lacinia", categories: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie),
                    ],
                suggestedMedia: [
                    TitledMedia(
                        title: "Suggested Media One",
                        content: [                MediaItemUI(id: "1113", title: "Movie One", posterUrl: "https://search.yahoo.com/search?p=noster", bannerUrl: "http://www.bing.com/search?q=lacinia", categories: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie),
                                                  MediaItemUI(id: "1113", title: "Movie One", posterUrl: "https://search.yahoo.com/search?p=noster", bannerUrl: "http://www.bing.com/search?q=lacinia", categories: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie),
                                                  MediaItemUI(id: "1113", title: "Movie One", posterUrl: "https://search.yahoo.com/search?p=noster", bannerUrl: "http://www.bing.com/search?q=lacinia", categories: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie),
                                                  MediaItemUI(id: "1113", title: "Movie One", posterUrl: "https://search.yahoo.com/search?p=noster", bannerUrl: "http://www.bing.com/search?q=lacinia", categories: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie),
                                                  MediaItemUI(id: "1113", title: "Movie One", posterUrl: "https://search.yahoo.com/search?p=noster", bannerUrl: "http://www.bing.com/search?q=lacinia", categories: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie),
                    ]),
                    TitledMedia(
                        title: "Suggested Media Two",
                        content: [                MediaItemUI(id: "1113", title: "Movie One", posterUrl: "https://search.yahoo.com/search?p=noster", bannerUrl: "http://www.bing.com/search?q=lacinia", categories: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie),
                                                  MediaItemUI(id: "1113", title: "Movie One", posterUrl: "https://search.yahoo.com/search?p=noster", bannerUrl: "http://www.bing.com/search?q=lacinia", categories: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie),
                                                  MediaItemUI(id: "1113", title: "Movie One", posterUrl: "https://search.yahoo.com/search?p=noster", bannerUrl: "http://www.bing.com/search?q=lacinia", categories: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie),
                                                  MediaItemUI(id: "1113", title: "Movie One", posterUrl: "https://search.yahoo.com/search?p=noster", bannerUrl: "http://www.bing.com/search?q=lacinia", categories: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie),
                                                  MediaItemUI(id: "1113", title: "Movie One", posterUrl: "https://search.yahoo.com/search?p=noster", bannerUrl: "http://www.bing.com/search?q=lacinia", categories: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie),
                    ]),
                ],
                onMediaItemClicked: {_ in }
            )
        }
    }
}
