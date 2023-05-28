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
    
    @StateObject private var viewModel = DiscoveryViewModel()
    @State private var mediaPreviewSheet: PreviewMedia = PreviewMedia(media: nil,sheetVisible: false)
    @State private var selectedMedia: MediaItemUI? = nil
    @State private var showCollectionDialog = false
    @State private var showGenreDialog = false

    init(onInfoClicked: @escaping () -> Void) {
        self.onInfoClicked = onInfoClicked
        NapierProxyKt.debugBuild()
    }
    
    var body: some View {
        var blurAmount: Float = {
            if isActive {
                return 4
            } else {
                return 0
            }
        }()
        
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
                        viewModel.submitAction(action: DiscoveryAction.FetchContent(mediaType: 0))
                    case .movies:
                        showGenreDialog = false
                        viewModel.submitAction(action: DiscoveryAction.FetchContent(mediaType: 1))
                    case .series:
                        showGenreDialog = false
                        viewModel.submitAction(action: DiscoveryAction.FetchContent(mediaType: 2))
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
                                                mediaPreviewSheet
                                                    .showSheet(media: singleItem)
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
                                                        mediaPreviewSheet
                                                            .showSheet(media: item)
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
                        title: "Genres",
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
        .onAppear {
            print("IOS - discovery - starting to observe viewmodel")
            viewModel.observe()
            viewModel.submitAction(action: DiscoveryAction.FetchContent(mediaType: 0))
        }
        .onDisappear {
            print("IOS - discovery - disposing viewmodel")
            viewModel.dispose()
        }
        .sheet(isPresented: $mediaPreviewSheet.sheetVisible) {
            if let itemToPreview = mediaPreviewSheet.media {
                DetailPreviewSheet(
                    mediaItem: itemToPreview,
                    onCloseClick: {
                        mediaPreviewSheet.hideSheet()
                    },
                    onMyCollectionClick: { item in
                        showCollectionDialog = true
                        mediaPreviewSheet.hideSheet()
                    }
                )
                .presentationDetents([.medium, .fraction(0.4)])
                .presentationDragIndicator(.hidden)
            } else {
//                fatalError("item to show was nil!")
            }
        }
    }
}

struct discoveryScreen_Previews: PreviewProvider {
    static var previews: some View {
        DiscoveryScreen(
            onInfoClicked: {}
        )
    }
}
