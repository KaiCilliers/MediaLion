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
    
    @State private var mediaPreviewSheet: MediaItemUiIdentifiable? = nil
    @State private var mediaPreviewSheetInner: MediaItemUiIdentifiable? = nil
    
    @State private var showCollectionDialog = false
    @State private var showCollectionDialogInner = false
    
    @State private var selectedMedia: MediaItemUI? = nil
    @State private var fullScreenGridContent: MediaWithTitle? = nil
    
    @State private var editTitleMode: Bool = false
    @State private var newCollectionTitle: String? = nil
    
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
                                
                                if(!media.isEmpty) {
                                    MLTitledMediaRow(
                                        rowTitle: rowTitle,
                                        media: media,
                                        onMediaItemClicked: { mediaItem in
                                            mediaPreviewSheet = MediaItemUiIdentifiable(media: mediaItem)
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
        .sheet(item: $fullScreenGridContent, onDismiss: {
            mediaPreviewSheetInner = nil
            showCollectionDialogInner = false
        }) { content in
            let columns = [GridItem(.flexible()), GridItem(.flexible()), GridItem(.flexible())]
            
            ZStack{
                ScrollView{
                    
                    if (editTitleMode) {
                        TextField(
                            content.title,
                            text: $newCollectionTitle.toUnwrapped(defaultValue: ""),
                            onEditingChanged: { (isBegin) in
                                if isBegin {
                                    newCollectionTitle = content.title
                                    print("Begins editing")
                                } else {
                                    print("Finishes editing")
                                }
                            },
                            onCommit: {
                                if(newCollectionTitle == nil) {
                                    newCollectionTitle = content.title
                                }
                                print("commit, old=\(content.title) and new=\(newCollectionTitle ?? "")")
                                viewModel.submitAction(action: CollectionAction.RenameCollection(oldCollectionName: content.title, newCollectionName: newCollectionTitle ?? ""))
                                editTitleMode = false
                            }
                        )
                        .foregroundColor(.white)
                        .customFont(.h2)
                        .padding(.leading, 16)
                        .padding(.top, 16)
                        
                    } else {
                        Text(newCollectionTitle ?? content.title)
                            .foregroundColor(.white)
                            .customFont(.h2)
                            .padding(.leading, 16)
                            .padding(.top, 16)
                            .frame(maxWidth: .infinity, alignment: .leading)
                            .onTapGesture {
                                editTitleMode = true
                            }
                    }
                    
                    LazyVGrid(columns: columns, spacing: 16) {
                        ForEach(content.media, id: \.self) { item in
                            MLMediaPoster(media: item)
                                .frame(width: 100,height: 170)
                                .onTapGesture {
                                    mediaPreviewSheetInner = MediaItemUiIdentifiable(media: item)
                                    selectedMedia = item
                                }
                        }
                    }
                }
                
                if showCollectionDialogInner {
                    MLCollectionsDialog(
                        onDismiss: { showCollectionDialogInner = false },
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
                .sheet(item: $mediaPreviewSheetInner) { mediaItem in
                    DetailPreviewSheet(
                        mediaItem: mediaItem.media,
                        onCloseClick: {
                            mediaPreviewSheetInner = nil
                        },
                        onMyCollectionClick: { item in
                            showCollectionDialogInner = true
                            mediaPreviewSheetInner = nil
                        }
                    )
                    .presentationDetents([.medium, .fraction(0.4)])
                    .presentationDragIndicator(.hidden)
                }
                .overlay {
                    VStack{
                        Spacer()
                        HStack{
                            Spacer()
                            Button {
                                EditButton()
                            }label: {
                                Image("editIcon")
                                    .resizable()
                                    .frame(width: 90, height: 90)
                            }
                        }
                    }
                    .padding(.bottom, 20)
                    .padding(.trailing, 40)
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

extension Binding {
     func toUnwrapped<T>(defaultValue: T) -> Binding<T> where Value == Optional<T>  {
        Binding<T>(get: { self.wrappedValue ?? defaultValue }, set: { self.wrappedValue = $0 })
    }
}
