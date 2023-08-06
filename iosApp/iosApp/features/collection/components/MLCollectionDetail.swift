//
//  MLCollectionDetail.swift
//  iosApp
//
//  Created by Nadine Cilliers on 05/08/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct MLCollectionDetail: View {
    
    let collection: CollectionNew
    @State var modifiableCollection: CollectionNew
    @ObservedObject var collectionViewModel: CollectionViewModel
    let closeScreen: () -> Void
    
    init(collection: CollectionNew, collectionViewModel: CollectionViewModel, closeScreen: @escaping () -> Void) {
        self.collection = collection
        _modifiableCollection = State(initialValue: collection)
        self.collectionViewModel = collectionViewModel
        self.closeScreen = closeScreen
        _newTitle = State(initialValue: "\(collection.title())")
    }
    
    @State private var editMode: Bool = false
    @State private var newTitle: String = ""
    
    @State private var mediaPreviewSheet: MediaItemUiIdentifiable? = nil
    @State private var selectedMedia: MediaItemUI? = nil
    @State private var showMiniCollectionDialog = false
    
    func updateCollection(collection: CollectionNew) {
        collectionViewModel.submitAction(action: UpdateCollection(collection: collection))
    }
    
    var body: some View {
        ZStack{
            ScrollView{
                
                // Title
                if(!editMode || "\(collection.title())" == "Favorites") {
                    Text("\(modifiableCollection.title())")
                        .foregroundColor(.white)
                        .customFont(.h2)
                        .padding(.leading, 16)
                        .padding(.top, 16)
                        .frame(maxWidth: .infinity, alignment: .leading)
                } else {
                    TextField(
                        "\(collection.title())",
                        text: $newTitle
                    )
                    .foregroundColor(.white)
                    .customFont(.h2)
                    .padding(.leading, 16)
                    .padding(.top, 16)
                }
                
                // Delete Collections UI
                if(editMode && "\(collection.title())" != "Favorites") {
                    Text("Delete Collection")
                        .foregroundColor(Color.red)
                        .customFont(.subtitle2)
                        .padding(.trailing, 16)
                        .underline()
                        .padding(.top, 16)
                        .frame(maxWidth: .infinity, alignment: .trailing)
                        .onTapGesture {
                            collectionViewModel.submitAction(action: DeleteCollection(collection: self.collection))
                            closeScreen()
                        }
                }
                
                // Content
                let columns = [GridItem(.flexible()), GridItem(.flexible()), GridItem(.flexible())]
                let mediaUi = collection.media().map { domainItem in
                    MediaItemUI.companion.from(domain: domainItem)
                }
                LazyVGrid(columns: columns, spacing: 16) {
                    ForEach(mediaUi, id: \.self) { item in
                        ZStack {
                            MLMediaPoster(
                                title: item.title,
                                posterUrl: item.posterUrl
                            )
                                .frame(width: 100,height: 170)
                                .onTapGesture {
                                    mediaPreviewSheet = MediaItemUiIdentifiable(media: item)
                                    selectedMedia = item
                                }
                            if editMode {
                                Button {
                                    modifiableCollection.remove(item: mediaItemToDomain(item: item))
                                    updateCollection(collection: modifiableCollection)
                                } label: {
                                    Image(systemName: "xmark.square.fill")
                                        .font(.title)
                                        .symbolRenderingMode(.palette)
                                        .foregroundStyle(.white, Color.red)
                                }
                            }
                        }
                    }
                }
                .environment(\.editMode, .constant(self.editMode ? EditMode.active : EditMode.inactive))
                
                if showMiniCollectionDialog {
                    MLCollectionsDialog(
                        onDismiss: { showMiniCollectionDialog = false },
                        targetMediaItem: mediaItemToDomain(item: selectedMedia!),
                        miniCollectionUiState: collectionViewModel.miniCollectionState,
                        onUpdateCollection: { collection in
                            collectionViewModel.submitAction(action: UpdateCollection(collection: collection))
                        },
                        onCreateCollection: { collection in
                            collectionViewModel.submitAction(action: InsertCollection(collection: collection))
                        }
                    )
                }
                
            }
                
        }
        .background(Color.background)
        .sheet(item: $mediaPreviewSheet) { mediaItem in
            MLDetailPreviewSheet(
                mediaItem: mediaItem.media,
                onCloseClick: {
                    mediaPreviewSheet = nil
                },
                onMyCollectionClick: { item in
                    showMiniCollectionDialog = true
                    mediaPreviewSheet = nil
                }
            )
            .presentationDetents([.medium, .fraction(0.4)])
            .presentationDragIndicator(.hidden)
        }
        .overlay {
            MLEditCollectionButton(
                isEditing: editMode,
                updateEditing: {
                    editMode = $0
                    if (!editMode) {
                        let trimmedNewTitle = newTitle.trimmingCharacters(in: .whitespacesAndNewlines)
                        if (!trimmedNewTitle.isEmpty) {
                            modifiableCollection = modifiableCollection.rename(newTitle: Title(value: trimmedNewTitle))
                            updateCollection(collection: modifiableCollection)
                        }
                    
                        newTitle = "\(modifiableCollection.title())"
                        print("newTitle is \(newTitle)")
                    }
                }
            )
        }
    }
}

struct MLCollectionDetail_Previews: PreviewProvider {
    static var previews: some View {
        MLCollectionDetail(
            collection: CollectionNewDef(
                name: "My Collection",
                media: [
                    SingleMediaItemMovie(name: "Movie One"),
                    SingleMediaItemMovie(name: "Movie Two"),
                    SingleMediaItemMovie(name: "Movie Three"),
                    SingleMediaItemMovie(name: "Movie Four"),
                ]
            ),
            collectionViewModel: CollectionViewModel(),
            closeScreen: {}
        )
    }
}
