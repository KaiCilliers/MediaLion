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
    
    @ObservedObject var discoveryViewModel: DiscoveryViewModel
    @State private var selectedMedia: MediaItemUI? = nil
    @State private var showCollectionDialog = false
    @State private var showCategoriesDialog = false
    @State private var mediaPreviewSheet: MediaItemUiIdentifiable? = nil
    
    var body: some View {
        
        ZStack {
            DiscoveryScreenContent(
                discoveryState: discoveryViewModel.discoveryState,
                fetchContentForPage: { discoveryViewModel.submitAction(action: FetchPageMediaContent(page: $0)) },
                showCategoryDialog: {
                    print("Ok i pressed the categories item")
                    showCategoriesDialog = true
                },
                showInfoDialog: { onInfoClicked() },
                showMediaPreview: {
                    mediaPreviewSheet = MediaItemUiIdentifiable(media: $0)
                    selectedMedia = $0
                }
            )
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
            
            if showCategoriesDialog {
                MLCategoriesDialog(
                    state: discoveryViewModel.categoriesState,
                    onDismiss: { showCollectionDialog = false },
                    onSelection: { discoveryViewModel.submitAction(action: FetchMediaForCategory(category: $0.origin)) }
                )
            }
            
            if showCollectionDialog {
                MLCollectionsDialog(
                    onDismiss: { showCategoriesDialog = false},
                    targetMediaItem: mediaItemToDomain(item: selectedMedia!),
                    miniCollectionUiState: discoveryViewModel.miniCollectionState,
                    onUpdateCollection: { discoveryViewModel.submitAction(action: UpdateCollection(collection: $0)) },
                    onCreateCollection: { discoveryViewModel.submitAction(action: InsertCollection(collection: $0)) }
                )
            }
        }
    }
}

struct discoveryScreen_Previews: PreviewProvider {
    static var previews: some View {
        DiscoveryScreen(
            onInfoClicked: {},
            discoveryViewModel: DiscoveryViewModel()
        )
    }
}

struct MediaWithTitleIOS : Identifiable {
    let origin: MediaWithTitleDef
    var id: ObjectIdentifier
    
    init(origin: MediaWithTitleDef) {
        self.origin = origin
        self.id = ObjectIdentifier(origin)
    }
}
