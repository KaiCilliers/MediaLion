//
//  Temp.swift
//  iosApp
//
//  Created by Nadine Cilliers on 05/08/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct Temp: View {
    
    let editTitleMode: Bool
    let collection: CollectionsNewIOS
    let updateCollection: (CollectionsNewIOS) -> Void
    let updateCurrentTitle: (String) -> Void
    let currentCollectionTitle: String
    
    var body: some View {
        let columns = [GridItem(.flexible()), GridItem(.flexible()), GridItem(.flexible())]
        
        ZStack{
            ScrollView{
                
                if (editTitleMode) {
                    TextField(
                        text: currentCollectionTitle,
                        onEditingChanged: { (isBegin) in
                            if isBegin {
                                updateCurrentTitle("\(collection.origin.title())")
                                print("Begins editing")
                            } else {
                                print("Finishes editing")
                            }
                        },
                        onCommit: {
                            UpdateCollection(collection: currentCollectionTitle)
                            
//                                    viewModel.submitAction(action: CollectionAction.RenameCollection(oldCollectionName: content.title, newCollectionName: newCollectionTitle?.trimmingCharacters(in: .whitespacesAndNewlines) ?? ""))
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
                            if(!staticCollections.contains(newCollectionTitle ?? content.title)) {
                                editTitleMode = true
                            }
                        }
                }
                
                //                    if (isEditingCollection && !staticCollections.contains(newCollectionTitle ?? content.title)) {
                //                        Text("Delete Collection")
                //                            .foregroundColor(Color.red)
                //                            .customFont(.subtitle2)
                //                            .padding(.trailing, 16)
                //                            .underline()
                //                            .padding(.top, 16)
                //                            .frame(maxWidth: .infinity, alignment: .trailing)
                //                            .onTapGesture {
                ////                                viewModel.submitAction(action: CollectionAction.DeleteCollection(collectionName: newCollectionTitle ?? content.title))
                //                                fullScreenGridContent = nil
                //                            }
                //                    }
                
                //                    LazyVGrid(columns: columns, spacing: 16) {
                //                        ForEach(content.media, id: \.self) { item in
                //                            ZStack {
                //                                MLMediaPoster(media: item)
                //                                    .frame(width: 100,height: 170)
                //                                    .onTapGesture {
                //                                        mediaPreviewSheetInner = MediaItemUiIdentifiable(media: item)
                //                                        selectedMedia = item
                //                                    }
                //                                    if isEditingCollection {
                //                                        Button {
                ////                                            viewModel.submitAction(action: CollectionAction.RemoveFromCollection(
                ////                                                collectionName: content.title,
                ////                                                mediaId: item.id,
                ////                                                mediaType: item.mediaType
                ////                                            ))
                //
                //                                            let mediaCache = content.media
                //                                            let new = mediaCache.filter { i in
                //                                                i.id != item.id
                //                                            }
                //
                //                                            fullScreenGridContent = MediaWithTitle(
                //                                                title: content.title,
                //                                                media: new
                //                                            )
                //
                //                                        } label: {
                //                                            Image(systemName: "xmark.square.fill")
                //                                                .font(.title)
                //                                                .symbolRenderingMode(.palette)
                //                                                .foregroundStyle(.white, Color.red)
                //                                        }
                //                                    }
                //
                //                            }
                //                        }
                //                    }
                //                    .environment(\.editMode, .constant(self.isEditingCollection ? EditMode.active : EditMode.inactive))
                //                }
                
                //                if showCollectionDialogInner {
                //                    MLCollectionsDialog(
                //                        onDismiss: { showCollectionDialogInner = false },
                //                        collections: searchViewModel.collectionState.compactMap({ $0 as? CollectionWithMedia }).map({ singleCollection in
                //
                //                            let selectedMedia = selectedMedia
                //                            var checked = false
                //
                //                            if (selectedMedia != nil) {
                //                                let collectionsWithMediaIds = singleCollection.contents.map { mediaItem in
                //                                    return mediaItem.id
                //                                }
                //
                //                                checked = collectionsWithMediaIds.contains(selectedMedia!.id)
                //                            }
                //
                //                            return CollectionItem(name: singleCollection.name as! String, checked: checked)
                //
                //                        }),
                //                        onAddToCollection: { collectionName in
                //                            if let selectedMediaItem = selectedMedia {
                ////                                searchViewModel.submitAction(
                ////                                    action: SearchAction.AddToCollection(
                ////                                        collectionName: collectionName,
                ////                                        mediaId: selectedMediaItem.id,
                ////                                        mediaType: selectedMediaItem.mediaType)
                ////                                )
                //                            }
                //                        },
                //                        onRemoveFromCollection: { collectionName in
                //                            if let selectedMediaItem = selectedMedia {
                ////                                searchViewModel.submitAction(
                ////                                    action: SearchAction.RemoveFromCollection(
                ////                                        collectionName: collectionName,
                ////                                        mediaId:selectedMediaItem.id,
                ////                                        mediaType: selectedMediaItem.mediaType
                ////                                    )
                ////                                )
                //                            }
                //                        },
                //                        onCreateNewCollection: { collectionName in
                ////                            searchViewModel.submitAction(action: SearchAction.CreateCollection(collectionName: collectionName))
                //                        }
                //                    )
                //                }
            }
            .background(Color.background)
            .sheet(item: $mediaPreviewSheetInner) { mediaItem in
                //                    MLDetailPreviewSheet(
                //                        mediaItem: mediaItem.media,
                //                        onCloseClick: {
                //                            mediaPreviewSheetInner = nil
                //                        },
                //                        onMyCollectionClick: { item in
                //                            showCollectionDialogInner = true
                //                            mediaPreviewSheetInner = nil
                //                        }
                //                    )
                //                    .presentationDetents([.medium, .fraction(0.4)])
                //                    .presentationDragIndicator(.hidden)
            }
            .overlay {
                //                    VStack{
                //                        Spacer()
                //                        HStack{
                //                            Spacer()
                //                            Button {
                //                                isEditingCollection.toggle()
                //                            } label: {
                //                                if self.isEditingCollection {
                //                                    Image("nameCreated")
                //                                        .resizable()
                //                                        .frame(width: 90, height: 90)
                //                                } else {
                //                                    Image("editIcon")
                //                                        .resizable()
                //                                        .frame(width: 90, height: 90)
                //                                }
                //                            }
                //                        }
                //                    }
                //                    .padding(.bottom, 20)
                //                    .padding(.trailing, 40)
            }
        }
    }
}

struct Temp_Previews: PreviewProvider {
    static var previews: some View {
        Temp()
    }
}
