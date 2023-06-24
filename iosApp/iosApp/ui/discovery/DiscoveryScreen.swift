//
//  discoveryScreen.swift
//  iosApp
//
//  Created by Nadine Cilliers on 02/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct DiscoveryScreen: View {
    
    @State var isActive : Bool = false
    let onInfoClicked: () -> Void
    
    @ObservedObject var viewModel: DiscoveryViewModel
    @State private var selectedMedia: MediaItemUI? = nil
    @State private var showCollectionDialog = false
    @State private var showGenreDialog = false
    @State private var mediaPreviewSheet: MediaItemUiIdentifiable? = nil
    
    var body: some View {
   
        
        ZStack{
            
            VStack (alignment: .center, spacing: 0){
                HStack{
                    
                    NavigationLink(value: MLRootDestinations.search) {
                        Image("searchIcon")
                            .resizable()
                            .frame(width: 27, height: 30)
                    }
                    
                    Spacer()
                    Image("logoIcon")
                        .resizable()
                        .frame(width: 100, height: 80)
                        .onTapGesture {
                            fatalError("Test Crash debug")
                        }
                    Spacer()
                    
                    Button {
                        onInfoClicked()
                    } label: {
                        Image("aboutIcon")
                            .resizable()
                            .frame(width: 30, height: 30)
                    }
                    
                }
                .padding()
                .background(Color.background)
                
                DiscoveryFilterItems( action: { filter in
                    switch (filter){
                    case .all:
                        showGenreDialog = false
                        viewModel.submitAction(action: DiscoveryAction.FetchContent(mediaType: nil))
                    case .movies:
                        showGenreDialog = false
                        viewModel.submitAction(action: DiscoveryAction.FetchContent(mediaType: MediaType.movie))
                    case .series:
                        showGenreDialog = false
                        viewModel.submitAction(action: DiscoveryAction.FetchContent(mediaType: MediaType.tv))
                    case .categories:
                        print("leka joshua - \(showGenreDialog)")
                        showGenreDialog = true
                        
                    }
                }
                )
               
                    ScrollView {
                        VStack (alignment: .center, spacing: 0){
                            switch(viewModel.state) {
                                
                            case let loadingState as DiscoveryState.Loading:
                                LoadingScreen()
                            case let errorState as DiscoveryState.Error:
                                Text("Error...")
                                    .foregroundColor(Color.white)
                                
                            case let contentState as DiscoveryState.Content:
                                if (contentState.media.count > 1) {
                                    ForEach(contentState.media, id: \.self) { item in
                                        MLTitledMediaRow(
                                            rowTitle: item.title,
                                            media: item.content,
                                            onMediaItemClicked: {
                                                singleItem in
                                                mediaPreviewSheet = MediaItemUiIdentifiable(media: singleItem)
                                                selectedMedia = singleItem
                                            })
                                    }
                                } else {
                                    ForEach(contentState.media, id: \.self) { titledMedia in
                                        Text(titledMedia.title)
                                            .foregroundColor(.white)
                                            .customFont(.h2)
                                            .padding(.leading, 16)
                                            .padding(.top, 16)
                                            .frame(maxWidth: .infinity, alignment: .leading)
                                    LazyVGrid(
                                        columns: [GridItem(.flexible()), GridItem(.flexible()), GridItem(.flexible())],
                                        spacing: 16
                                    ) {
                                            ForEach(titledMedia.content, id: \.self) { item in
                                                MLMediaPoster(media: item)
                                                    .frame(width: 100,height: 170)
                                                    .onTapGesture {
                                                        mediaPreviewSheet = MediaItemUiIdentifiable(media: item)
                                                        selectedMedia = item
                                                    }
                                            }
                                        }
                                    }
                                }
                                
                            default:
                                fatalError("state should not be reachable \(viewModel.state)")
                            }
                       
                        }
                }.padding(.bottom, 30)
            }
            .background(Color.background)
            
            if showCollectionDialog {
                MLCollectionsDialog(
                    onDismiss: { showCollectionDialog = false },
                    collections: viewModel.collectionState.compactMap({ $0 as? CollectionWithMedia }).map({ singleCollection in
                      
                        let selectedMedia = selectedMedia
                        var checked = false
                        
                        if (selectedMedia != nil) {
                            let collectionsWithMediaIds = singleCollection.contents.map { mediaItem in
                                return mediaItem.id
                            }
                            
                            checked = collectionsWithMediaIds.contains(selectedMedia!.id)
                        }
                        
                        return CollectionItem(name: singleCollection.name as! String, checked: checked)
                        
                    }),
                    onAddToCollection: { collectionName in
                        if let selectedMediaItem = selectedMedia {
                            viewModel.submitAction(
                                action: DiscoveryAction.AddToCollection(
                                    collectionName: collectionName,
                                    mediaId: selectedMediaItem.id,
                                    mediaType: selectedMediaItem.mediaType)
                            )
                        }
                    },
                    onRemoveFromCollection: { collectionName in
                        if let selectedMediaItem = selectedMedia {
                            viewModel.submitAction(
                                action: DiscoveryAction.RemoveFromCollection(
                                    collectionName: collectionName,
                                    mediaId:selectedMediaItem.id,
                                    mediaType: selectedMediaItem.mediaType
                                )
                            )
                        }
                    },
                    onCreateNewCollection: { collectionName in
                        viewModel.submitAction(action: DiscoveryAction.CreateCollection(collectionName: collectionName))
                    }
                )
            }
            
            if showGenreDialog {
                let a = print("leka joshua - show genres \(viewModel.genreState)")
                let genres = viewModel.genreState
                if(genres is GenreState.Genres) {
                    CustomCategoriesDialog(
                        genres: (genres as! GenreState.Genres).all.map({ item in
                            return GenreWithType(
                                genre: item,
                                mediaType: item.mediaType
                            )
                        }),
                        title: "Categories",
                        onClose: { genre in
                            viewModel.submitAction(action: DiscoveryAction.FetchGenreContent(
                                genreId: Int32(genre.genre.id),
                                mediaType: genre.mediaType
                            ))
                            showGenreDialog = false
                        }
                    )
                }
            }
            
        }
        .sheet(item: $mediaPreviewSheet) { mediaItem in
            DetailPreviewSheet(
                mediaItem: mediaItem.media,
                onCloseClick: {
                    mediaPreviewSheet = nil
                },
                onMyCollectionClick: { item in
                    showCollectionDialog = true
                    mediaPreviewSheet = nil
                }
            )
            .presentationDetents([.medium, .fraction(0.4)])
            .presentationDragIndicator(.hidden)
        }
    }
}

struct discoveryScreen_Previews: PreviewProvider {
    static var previews: some View {
        DiscoveryScreen(
            onInfoClicked: {},
            viewModel: DiscoveryViewModel()
        )
    }
}
