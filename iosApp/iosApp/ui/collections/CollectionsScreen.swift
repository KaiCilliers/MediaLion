//
//  collectionScreen.swift
//  iosApp
//
//  Created by Nadine Cilliers on 02/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct CollectionsScreen: View {
    
    let onInfoClicked: () -> Void
    @StateObject var viewModel = CollectionViewModel()
    @StateObject var searchViewModel = SearchViewModel()
    
    @State private var mediaPreviewSheet: PreviewMedia = PreviewMedia(media: nil,sheetVisible: false)
    @State private var mediaPreviewSheetInner: PreviewMedia = PreviewMedia(media: nil,sheetVisible: false)
    @State private var showCollectionDialog = false
    @State private var showCollectionDialogInner = false
    @State private var selectedMedia: MediaItemUI? = nil
    @State private var fullScreenGridContent: MediaWithTitle? = nil
    
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
                
                
                ScrollView {
                    VStack (alignment: .center, spacing: 0){
                        switch(viewModel.state) {
                            
                        case let emptyState as CollectionState.Empty:
                            Text("empty")
                            
                        case let loadingState as CollectionState.Loading:
                            LoadingScreen()
                            
                        case let withCollectionsState as CollectionState.AllCollections:
                            ForEach(withCollectionsState.collections as [CollectionWithMediaUI], id: \.self) { c in
                                
                                let rowTitle = c.name as! String
                                let media = c.contents
                                
                                MLTitledMediaRow(
                                    rowTitle: rowTitle,
                                    media: media,
                                    onMediaItemClicked: { mediaItem in
                                        mediaPreviewSheet.showSheet(media: mediaItem)
                                        selectedMedia = mediaItem
                                    },
                                    onTitleClicked: {
                                        fullScreenGridContent = MediaWithTitle(
                                            title: rowTitle,
                                            media: media
                                        )
                                    }
                                )
                            }
                            
                        default:
                            fatalError("unknown state for collections screen - \(viewModel.state)")
                        }
                    }
                }
            }
            
            if showCollectionDialog {
                MLCollectionsDialog(
                    onDismiss: { showCollectionDialog = false },
                    collections: searchViewModel.collectionState.compactMap({ $0 as? CollectionWithMedia }).map({ singleCollection in
                        
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
                            searchViewModel.submitAction(
                                action: SearchAction.AddToCollection(
                                    collectionName: collectionName,
                                    mediaId: selectedMediaItem.id,
                                    mediaType: selectedMediaItem.mediaType)
                            )
                        }
                    },
                    onRemoveFromCollection: { collectionName in
                        if let selectedMediaItem = selectedMedia {
                            searchViewModel.submitAction(
                                action: SearchAction.RemoveFromCollection(
                                    collectionName: collectionName,
                                    mediaId:selectedMediaItem.id,
                                    mediaType: selectedMediaItem.mediaType
                                )
                            )
                        }
                    },
                    onCreateNewCollection: { collectionName in
                        searchViewModel.submitAction(action: SearchAction.CreateCollection(collectionName: collectionName))
                    }
                )
            }
            
        }
        .background(Color.background)
        .onAppear {
            print("IOS - collection - starting to observe viewModel")
            viewModel.observe()
            searchViewModel.observe()
        }
        .onDisappear {
            print("IOS - collection - disposing viewModel")
            viewModel.dispose()
            searchViewModel.dispose()
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
        .sheet(item: $fullScreenGridContent) { content in
            let columns = [GridItem(.flexible()), GridItem(.flexible()), GridItem(.flexible())]
            ZStack{
                ScrollView{
                    
                    Text(content.title)
                        .foregroundColor(.white)
                        .customFont(.h2)
                        .padding(.leading, 16)
                        .padding(.top, 16)
                        .frame(maxWidth: .infinity, alignment: .leading)
                    
                    LazyVGrid(columns: columns, spacing: 16) {
                        ForEach(content.media, id: \.self) { item in
                            MLMediaPoster(media: item)
                                .frame(width: 100,height: 170)
                                .onTapGesture {
                                    mediaPreviewSheetInner.showSheet(media: item)
                                    selectedMedia = item
                                }
                        }
                    }
                }
                
                if showCollectionDialogInner {
                    MLCollectionsDialog(
                        onDismiss: { showCollectionDialog = false },
                        collections: searchViewModel.collectionState.compactMap({ $0 as? CollectionWithMedia }).map({ singleCollection in
                            
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
                                searchViewModel.submitAction(
                                    action: SearchAction.AddToCollection(
                                        collectionName: collectionName,
                                        mediaId: selectedMediaItem.id,
                                        mediaType: selectedMediaItem.mediaType)
                                )
                            }
                        },
                        onRemoveFromCollection: { collectionName in
                            if let selectedMediaItem = selectedMedia {
                                searchViewModel.submitAction(
                                    action: SearchAction.RemoveFromCollection(
                                        collectionName: collectionName,
                                        mediaId:selectedMediaItem.id,
                                        mediaType: selectedMediaItem.mediaType
                                    )
                                )
                            }
                        },
                        onCreateNewCollection: { collectionName in
                            searchViewModel.submitAction(action: SearchAction.CreateCollection(collectionName: collectionName))
                        }
                    )
                }
            }
                .background(Color.background)
                .sheet(isPresented: $mediaPreviewSheetInner.sheetVisible) {
                    if let itemToPreview = mediaPreviewSheetInner.media {
                        DetailPreviewSheet(
                            mediaItem: itemToPreview,
                            onCloseClick: {
                                mediaPreviewSheetInner.hideSheet()
                            },
                            onMyCollectionClick: { item in
                                showCollectionDialogInner = true
                                mediaPreviewSheetInner.hideSheet()
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
    
    struct collectionScreen_Previews: PreviewProvider {
        static var previews: some View {
            CollectionsScreen(
                onInfoClicked: {}
            )
        }
    }
}

struct MediaWithTitle: Identifiable {
    var id: String { title }
    let title: String
    let media: [MediaItemUI]
}
