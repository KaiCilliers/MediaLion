//
//  SearchScreen.swift
//  iosApp
//
//  Created by Nadine Cilliers on 15/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared
typealias AColor = SwiftUI.Color

struct SearchScreen: View {
    
    @Environment(\.presentationMode) var presentationMode
    @StateObject private var searchViewModel = SearchViewModel()

    @State private var showAboutDialog: Bool = false
    @State private var mediaPreviewSheet: MediaItemUiIdentifiable? = nil
    @State private var showCollectionDialog = false
    @State private var selectedMedia: MediaItemUI? = nil
    @State private var currentQuery: String = ""
    
    var body: some View {
        
        let screenBlurAmount: Float = {
            if (showAboutDialog) {
                return 4
            } else {
                return 0
            }
        }()
        
        ZStack {
            VStack (alignment: .center, spacing: 0){
                
                HStack{
                    
                    Button {
                        self.presentationMode.wrappedValue.dismiss()
                    } label: {
                        Image("backArrowIcon")
                            .resizable()
                            .frame(width: 27, height: 30)
                    }
                    
                    Spacer()
                    
                    Button {
                        showAboutDialog = true
                    } label: {
                        Image("aboutIcon")
                            .resizable()
                            .frame(width: 30, height: 30)
                    }
                    
                }
                .padding()
                .background(Color.background)
                
                MLSearchBar(
                    text: currentQuery,
                    labelText: StringRes.emptySearch,
                    onSearchQueryTextChanged: { query in
                        currentQuery = query
                        searchViewModel.submitAction(action: SearchScreenAction.SubmitSearchQuery(query: SearchQueryDefault(value: query)))
                    },
                    onClearSearchText: {
                        currentQuery = ""
                        searchViewModel.submitAction(action: SearchScreenAction.SubmitSearchQuery(query: SearchQueryDefault(value: "")))
                    }
                )
                
                switch(searchViewModel.searchState) {
                    
                case let idleState as SearchUIState.TopSuggestions:
                    
                    let mediaFavTuple = idleState.media.map { mediaWithFav in
                        (MediaItemUI.companion.from(domain: mediaWithFav.mediaItem), mediaWithFav.favorited)
                    }
                    
                    SearchIdleState(
                        rowTitle: StringRes.topSuggestions,
                        mediaWithFavorites: mediaFavTuple,
                        onMediaClicked: { item in
                            mediaPreviewSheet = MediaItemUiIdentifiable(media: item)
                            selectedMedia = item
                        },
                        onFavoriteToggle: { item, favorited in
                            let collections = searchViewModel.miniCollectionState
                            var favCollection: CollectionNew? = nil
                            switch (collections) {
                                case let contentState as Content:
                                    favCollection = contentState.collections.first(where: {
                                        return $0.title() == Title(value: "Favorites")
                                        })
                                
                                    if (favorited) {
                                        favCollection?.add(item: mediaItemToDomain(item: item))
                                    } else {
                                        favCollection?.remove(item: mediaItemToDomain(item: item))
                                    }
                                
                                if let favCol = favCollection {
                                    searchViewModel.submitAction(action: UpdateCollection(collection: favCol))
                                }
                                   
                            default: do {}
                            }
                        }
                    )
                    
                case let resultState as SearchUIState.Results:
                    let res = resultState.results.results()
                    MLTitledMediaGrid(
                        gridTitle: "\(res.title())",
                        media: res.media().map({ domainItem in
                            MediaItemUI.companion.from(domain: domainItem)
                        }),
                        suggestedMedia: [],
                        onMediaItemClicked: { media in
                            mediaPreviewSheet = MediaItemUiIdentifiable(media: media)
                            selectedMedia = media
                        }
                    )
                    
                case _ as SearchUIState.Loading:
                    MLProgressIndicator()
                    
                case _ as SearchUIState.NoResults:
                    SearchEmptyState()
                    
                default:
                    fatalError("Unreachable state! \(searchViewModel.searchState)")
                }
            }
            .blur(radius: CGFloat(screenBlurAmount))
            .disabled(showAboutDialog || showCollectionDialog)
            
            if showAboutDialog {
                MLAboutDialog(
                    onCloseAction: {
                        showAboutDialog = false
                    }
                )
            }
            
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
            print("IOS - starting to observe viewModel")
            searchViewModel.observe()
        }
        .onDisappear {
            print("IOS - disposing viewModel")
            searchViewModel.dispose()
        }
        .sheet(item: $mediaPreviewSheet) { mediaToPreview in
            MLDetailPreviewSheet(
                mediaItem: mediaToPreview.media,
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
    
    struct SearchScreen_Previews: PreviewProvider {
        static var previews: some View {
            SearchScreen()
        }
    }
}

struct PreviewMedia {
    var media: MediaItemUI? = nil
    var sheetVisible: Bool = false
    
    mutating func showSheet(media: MediaItemUI) {
        self.media = media
        self.sheetVisible = true
    }
    
    mutating func hideSheet() {
        self.media = nil
        self.sheetVisible = false
    }
}

struct MediaItemUiIdentifiable: Identifiable {
    let id: UUID
    let media: MediaItemUI
    
    init(media: MediaItemUI) {
        self.id = UUID()
        self.media = media
    }
}

struct CollectionItemIdentifiable: Identifiable {
    let id: UUID
    let collection: CollectionNew
    
    init(collection: CollectionNew) {
        self.id = UUID()
        self.collection = collection
    }
}

func mediaItemToDomain(item: MediaItemUI) -> SingleMediaItemD {
    switch (item.mediaType) {
        case .movie:
            return SingleMediaItemMovie(
                id: IDDef(value: item.id),
                title: Title(value: item.title),
                backdropPath: item.bannerUrl,
                posterPath: item.posterUrl,
                categories: item.categories,
                popularity: item.popularity,
                voteAverage: item.voteAverage,
                adult: false,
                overview: item.overview,
                voteCount: item.voteCount,
                releaseDate: item.releaseYear
            )
        case .tv:
            return SingleMediaItemTVShow(
                id: IDDef(value: item.id),
                title: Title(value: item.title),
                backdropPath: item.bannerUrl,
                posterPath: item.posterUrl,
                categories: item.categories,
                popularity: item.popularity,
                voteAverage: item.voteAverage,
                adult: false,
                overview: item.overview,
                voteCount: item.voteCount,
                firstAirDate: item.releaseYear
            )
    default:
        fatalError("unknown media type \(item.mediaType) when converting media item to domain model")
    }
}
