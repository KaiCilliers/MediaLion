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
    @State private var showCollectionDialog = false
    @State private var selectedMedia: MediaItemUI? = nil
    @State private var showCollectionDetail: CollectionsNewIOS? = nil
    
    
    private let staticCollections = ["Favorites"]
    
    var body: some View {
        ZStack{
            
            CollectionScreenContent(
                collectionState: viewModel.collectionState,
                openInfoDialog: { onInfoClicked() },
                openMediaPreviewSheet: {
                    mediaPreviewSheet = MediaItemUiIdentifiable(media: $0)
                    selectedMedia = $0
                }
            )
            .disabled(showCollectionDialog || mediaPreviewSheet != nil)
            
            if showCollectionDialog {
                MLCollectionsDialog(
                    onDismiss: { showCollectionDialog = false },
                    targetMediaItem: mediaItemToDomain(item: selectedMedia!),
                    miniCollectionUiState: searchViewModel.miniCollectionState,
                    onUpdateCollection: { collection in
                        searchViewModel.submitAction(action: UpdateCollection(collection: collection))
                    },
                    onCreateCollection: { collection in
                        searchViewModel.submitAction(action: InsertCollection(collection: collection))
                    }
                )
            }
            
        }
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
            MLDetailPreviewSheet(
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
        .sheet(item: $showCollectionDetail) { collection in
            MLCollectionDetail(
                collection: collection.origin,
                updateCollection: { updatedCollection in
                    
                },
                collectionViewModel: viewModel,
                closeScreen: { showCollectionDetail = nil }
            )
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
