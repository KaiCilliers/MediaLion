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
                
                DiscoveryFilterItems( action: {filter in
                    if filter == .categories {
                        isActive = true
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
                            ForEach(contentState.media, id: \.self) { item in
                                MLTitledMediaRow(
                                    rowTitle: item.title,
                                    media: item.content,
                                    onMediaItemClicked: {
                                        singleItem in
                                            print("wolverine man - \(singleItem.title)")
                                            mediaPreviewSheet
                                                .showSheet(media: singleItem)
                                            selectedMedia = singleItem
                                    })
                            }
                            
                            
                        default:
                            fatalError("state should not be reachable \(viewModel.state)")
                        }
                    }
                }
                
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
            
        }
        .onAppear {
            print("IOS - discovery - starting to observe viewmodel")
            viewModel.observe()
            viewModel.submitAction(action: DiscoveryAction.FetchContent())
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
