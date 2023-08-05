////
////  MLMediaFavoriteListItem.swift
////  iosApp
////
////  Created by Nadine Cilliers on 15/04/2023.
////  Copyright © 2023 orgName. All rights reserved.
////
//
//import SwiftUI
//import shared
//
//struct MLMediaFavoriteListItem: View {
//
//    var mediaItem: MediaItemUI
//    var onFavoriteClick: (Bool) -> Void
//
//    var body: some View {
//        HStack(spacing: 0.0) {
//
//            MLMediaBanner(media: mediaItem)
//                .frame(width: 130.0, height: 90.0)
//                .padding(.trailing, 16.0)
//                
//            Text(mediaItem.title)
//                .multilineTextAlignment(.leading)
//                .customFont(.h4)
//                .foregroundColor(.white)
//                .padding(.leading, 8)
//
//            Spacer()
//
//            Button{
//                onFavoriteClick(!mediaItem.isFavorited)
//            } label: {
//                if (mediaItem.isFavorited) {
//                    Image("heartFilledIcon")
//                        .resizable()
//                        .frame(width: 35.0, height: 30.0)
//                        .padding(.horizontal, 16.0)
//                } else {
//                    Image("heartOutlineIcon")
//                        .resizable()
//                        .frame(width: 35.0, height: 30.0)
//                        .padding(.horizontal, 16.0)
//                }
//            }
//        }
//        
//        .background(Color.background)
//        
//    }
//}
//
//struct MLMediaFavoriteListItem_Previews: PreviewProvider {
//    static var previews: some View {
//        MLMediaFavoriteListItem(
//            mediaItem: MediaItemUI(id: 1113, title: "Movie One", isFavorited: true, posterUrl: "https://search.yahoo.com/search?p=noster", bannerUrl: "http://www.bing.com/search?q=lacinia", genreIds: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie),
//            onFavoriteClick: {_ in}
//        )
//    }
//}
