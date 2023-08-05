////
////  DetailPreviewTest.swift
////  iosApp
////
////  Created by Nadine Cilliers on 30/04/2023.
////  Copyright © 2023 orgName. All rights reserved.
////
//
//import SwiftUI
//import shared
//
//struct DetailPreviewSheet: View {
//    
//    let mediaItem: MediaItemUI
//    let onCloseClick: () -> Void
//    let onMyCollectionClick: (MediaItemUI) -> Void
//    
//    var body: some View {
//        ZStack{
//            
//            LinearGradient(gradient: Gradient(colors: [.mlPrimary, .primaryVariantBlue]), startPoint: .top, endPoint: .bottom).edgesIgnoringSafeArea(.all)
//            
//            VStack{
//                HStack{
//                    MLMediaPoster(media: mediaItem)
//                        .frame(width: 80, height: 120)
//                        .padding(.leading)
//                        .padding(.trailing, 8)
//                        .padding(.bottom)
//                    
//                VStack{
//                    Text(mediaItem.title)
//                        .foregroundColor(.white)
//                        .frame(maxWidth: .infinity, alignment: .topLeading)
//                        .customFont(.h3)
//                    
//                    Text(mediaItem.releaseYear)
//                        .foregroundColor(.white)
//                        .frame(maxWidth: .infinity, alignment: .topLeading)
//                        .padding(.bottom, 2)
//                        .customFont(.h1)
//                    
//                    Text(mediaItem.overview)
//                        .foregroundColor(.white)
//                        .frame(maxWidth: .infinity, alignment: .topLeading)
//                        .customFont(.body2)
//                        .lineSpacing(2)
//                    }
//                    
//                    Button {onCloseClick()} label: {
//                        Image("closeIcon")
//                            .resizable()
//                            .frame(width: 30, height: 30)
//                            .padding(.bottom, 105)
//                            .padding(.trailing)
//                            .padding(.leading, 5)
//                    }
//                    
//                }
//                
//                Spacer()
//                    .frame(height: 30)
//                
//                VStack {
//                    Button { onMyCollectionClick(mediaItem) } label: {
//                        VStack {
//                            Image("addToListIcon")
//                                .resizable()
//                                .frame(width: 40, height: 40)
//                            
//                            Text(StringRes.mtList)
//                                .customFont(.h1)
//                                .foregroundColor(.white)
//                        }
//                    }
//                }
//            }
//            
//        }
//    }
//    
//    struct DetailPreviewTest_Previews: PreviewProvider {
//        static var previews: some View {
//            DetailPreviewSheet(
//                mediaItem: MediaItemUI(id: 1113, title: "Harry Potter and the Philosopher's Stone", isFavorited: false, posterUrl: "https://image.tmdb.org/t/p/original/wuMc08IPKEatf9rnMNXvIDxqP4W.jpg", bannerUrl: "https://image.tmdb.org/t/p/original/hziiv14OpD73u9gAak4XDDfBKa2.jpg", genreIds: [], overview: "Throughout the series, Harry is described as having his father's perpetually untidy black hair, his mother's bright green eyes, and a lightning bolt-shaped scar on his forehead. He is further described as small and skinny for his age with a thin face and knobbly knees, and he wears Windsor glasses.", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "1996", mediaType: MediaType.movie),
//                onCloseClick: {},
//                onMyCollectionClick: {_ in}
//            )
//        }
//    }
//    
//    private struct DetailPrevewScreen: View {
//        @State private var showingDetailPreview = true
//        var body: some View {
//            Button("show bottom sheet"){
//                showingDetailPreview.toggle()
//            }
//            .sheet(isPresented: $showingDetailPreview){
//                
//                DetailPreviewSheet(
//                    mediaItem: MediaItemUI(id: 1113, title: "Movie One", isFavorited: false, posterUrl: "https://image.tmdb.org/t/p/original/wuMc08IPKEatf9rnMNXvIDxqP4W.jpg", bannerUrl: "https://image.tmdb.org/t/p/original/hziiv14OpD73u9gAak4XDDfBKa2.jpg", genreIds: [], overview: "electram", popularity: 20.21, voteAverage: 22.23, voteCount: 5474, releaseYear: "mentitum", mediaType: MediaType.movie),
//                    onCloseClick: {
//                        showingDetailPreview = false
//                    },
//                    onMyCollectionClick: {_ in}
//                )
//                .presentationDetents([.medium, .fraction(0.4)])
//                .presentationDragIndicator(.hidden)
//                
//                
//                
//            }
//            
//        }
//        
//    }
//    
//    struct DetailPrevewScreen_Previews: PreviewProvider {
//        static var previews: some View {
//            DetailPrevewScreen()
//        }
//    }
//}
