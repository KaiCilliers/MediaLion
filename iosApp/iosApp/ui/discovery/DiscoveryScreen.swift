////
////  discoveryScreen.swift
////  iosApp
////
////  Created by Nadine Cilliers on 02/05/2023.
////  Copyright © 2023 orgName. All rights reserved.
////
//
//import SwiftUI
//import shared
//
//struct DiscoveryScreen: View {
//    
//    @State var isActive : Bool = false
//    let onInfoClicked: () -> Void
//    
//    @ObservedObject var discoveryViewModel: DiscoveryViewModel
//    @State private var selectedMedia: MediaItemUI? = nil
//    @State private var showCollectionDialog = false
//    @State private var showGenreDialog = false
//    @State private var mediaPreviewSheet: MediaItemUiIdentifiable? = nil
//    
//    var body: some View {
//   
//        
//        ZStack{
//            
//            VStack (alignment: .center, spacing: 0){
//                HStack{
//                    
//                    NavigationLink(value: MLRootDestinations.search) {
//                        Image("searchIcon")
//                            .resizable()
//                            .frame(width: 27, height: 30)
//                    }
//                    
//                    Spacer()
//                    Image("logoIcon")
//                        .resizable()
//                        .frame(width: 100, height: 80)
//                    Spacer()
//                    
//                    Button {
//                        onInfoClicked()
//                    } label: {
//                        Image("aboutIcon")
//                            .resizable()
//                            .frame(width: 30, height: 30)
//                    }
//                    
//                }
//                .padding()
//                .background(Color.background)
//                
//                DiscoveryFilterItems( action: { filter in
//                    switch (filter){
//                    case .all:
//                        showGenreDialog = false
//                        discoveryViewModel.submitAction(action: FetchPageMediaContent(page: DiscoveryPage.All()))
//                    case .movies:
//                        showGenreDialog = false
//                        discoveryViewModel.submitAction(action: FetchPageMediaContent(page: DiscoveryPage.Movies()))
//                    case .series:
//                        showGenreDialog = false
//                        discoveryViewModel.submitAction(action: FetchPageMediaContent(page: DiscoveryPage.TVShows()))
//                    case .categories:
//                        showGenreDialog = true
//                    }
//                }
//                )
//               
//                    ScrollView {
//                        VStack (alignment: .center, spacing: 0){
//                            switch(discoveryViewModel.discoveryState) {
//                                
//                            case let loadingState as DiscoveryUIState.Loading:
//                                LoadingScreen()
//                            case let errorState as DiscoveryUIState.Error:
//                                Text("Error...")
//                                    .foregroundColor(Color.white)
//                                
//                            case let contentState as DiscoveryUIState.Content:
//                                if (contentState.media.all().count > 1) {
//                                    ForEach(contentState.media.all().map({ sharedModel in
//                                        MediaWithTitleIOS(origin: sharedModel as! MediaWithTitleDef)
//                                    })) { item in
//                                        MLTitledMediaRow(
//                                            rowTitle: "\(item.origin.title())",
//                                            media: item.origin.media().map({ domainItem in
//                                                MediaItemUI.companion.from(domain: domainItem)
//                                            }),
//                                            onMediaItemClicked: {
//                                                singleItem in
//                                                mediaPreviewSheet = MediaItemUiIdentifiable(media: singleItem)
//                                                selectedMedia = singleItem
//                                            })
//                                    }
//                                } else {
//                                    ForEach(contentState.media.all().map({ sharedModel in
//                                        MediaWithTitleIOS(origin: sharedModel as! MediaWithTitleDef)
//                                    })) { item in
//                                        Text("\(item.origin.title())")
//                                            .foregroundColor(.white)
//                                            .customFont(.h2)
//                                            .padding(.leading, 16)
//                                            .padding(.top, 16)
//                                            .frame(maxWidth: .infinity, alignment: .leading)
//                                        LazyVGrid(
//                                            columns: [GridItem(.flexible()), GridItem(.flexible()), GridItem(.flexible())],
//                                            spacing: 16
//                                        ) {
//                                            
////                                            ForEach(titledMedia, id: \.self) { item in
////                                                    MLMediaPoster(media: item)
////                                                        .frame(width: 100,height: 170)
////                                                        .onTapGesture {
////                                                            mediaPreviewSheet = MediaItemUiIdentifiable(media: item)
////                                                            selectedMedia = item
////                                                        }
////                                                }
////                                            }
//                                        }
//                                    }
//                                }
//                                
//                            default:
//                                fatalError("state should not be reachable \(discoveryViewModel.state)")
//                            }
//                       
//                        }
//                }.padding(.bottom, 30)
//            }
//            .background(Color.background)
//            
//            if showCollectionDialog {
//                MLCollectionsDialog(
//                    onDismiss: { showCollectionDialog = false },
//                    collections: viewModel.collectionState.compactMap({ $0 as? CollectionWithMedia }).map({ singleCollection in
//                      
//                        let selectedMedia = selectedMedia
//                        var checked = false
//                        
//                        if (selectedMedia != nil) {
//                            let collectionsWithMediaIds = singleCollection.contents.map { mediaItem in
//                                return mediaItem.id
//                            }
//                            
//                            checked = collectionsWithMediaIds.contains(selectedMedia!.id)
//                        }
//                        
//                        return CollectionItem(name: singleCollection.name as! String, checked: checked)
//                        
//                    }),
//                    // todo update UI to have less lambdas
//                    onAddToCollection: { collectionName in
//                        if let selectedMediaItem = selectedMedia {
//                            // todo add to collection
////                            discoveryViewModel.submitAction(
////                                action: DiscoveryAction.AddToCollection(
////                                    collectionName: collectionName,
////                                    mediaId: selectedMediaItem.id,
////                                    mediaType: selectedMediaItem.mediaType)
////                            )
//                        }
//                    },
//                    onRemoveFromCollection: { collectionName in
//                        if let selectedMediaItem = selectedMedia {
//                            // todo remove from collection
////                            discoveryViewModel.submitAction(
////                                action: DiscoveryAction.RemoveFromCollection(
////                                    collectionName: collectionName,
////                                    mediaId:selectedMediaItem.id,
////                                    mediaType: selectedMediaItem.mediaType
////                                )
////                            )
//                        }
//                    },
//                    onCreateNewCollection: { collectionName in
//                        // todo create collection
////                        discoveryViewModel.submitAction(action: DiscoveryAction.CreateCollection(collectionName: collectionName))
//                    }
//                )
//            }
//            
//            if showGenreDialog {
//                let a = print("leka joshua - show genres \(discoveryViewModel.genreState)")
//                let categories = discoveryViewModel.categoriesState
//                if(categories is CategoriesUIState.Content) {
//                    CustomCategoriesDialog(
//                        categories: categories.categories.map({ item in
//                            MediaCategoryIOS(origin: item)
//                        }),
//                        title: "Categories",
//                        onClose: { selectedCategory in
//                            discoveryViewModel.submitAction(action: FetchMediaForCategory(category: selectedCategory.origin))
//                            showGenreDialog = false
//                        }
//                    )
//                }
//            }
//            
//        }
//        .sheet(item: $mediaPreviewSheet) { mediaItem in
//            DetailPreviewSheet(
//                mediaItem: mediaItem.media,
//                onCloseClick: {
//                    mediaPreviewSheet = nil
//                },
//                onMyCollectionClick: { item in
//                    showCollectionDialog = true
//                    mediaPreviewSheet = nil
//                }
//            )
//            .presentationDetents([.medium, .fraction(0.4)])
//            .presentationDragIndicator(.hidden)
//        }
//    }
//}
//
//struct discoveryScreen_Previews: PreviewProvider {
//    static var previews: some View {
//        DiscoveryScreen(
//            onInfoClicked: {},
//            discoveryViewModel: DiscoveryViewModel()
//        )
//    }
//}
//
//struct MediaWithTitleIOS : Identifiable {
//    let origin: MediaWithTitleDef
//    var id: ObjectIdentifier
//    
//    init(origin: MediaWithTitleDef) {
//        self.origin = origin
//        self.id = ObjectIdentifier(origin)
//    }
//}
