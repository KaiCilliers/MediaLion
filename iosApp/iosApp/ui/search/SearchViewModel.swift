//
//  SearchViewModel.swift
//  iosApp
//
//  Created by Nadine Cilliers on 06/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

@MainActor class SearchViewModel: ObservableObject {
    
}


//val searchComponent = SearchComponent.Default(
//    movieDetails = MovieDetailsUseCase.Default(lookup()),
//    tvDetails = TVDetailsUseCase.Default(lookup()),
//    relatedDocumentariesUseCase = DocumentariesRelatedToUseCase.Default(lookup()),
//    relatedMoviesUseCase = MoviesRelatedToUseCase.Default(lookup()),
//    suggestedMediaUseCase = SuggestedMediaUseCase.Default(lookup()),
//    topMediaResultsUseCase = TopMediaResultsUseCase.Default(
//        movieRepo = lookup(),
//        tvRepo = lookup(),
//        tvMapper = Mapper.TVShowEntity.DomainToMediaDomain(),
//        movieMapper = Mapper.MovieEntity.DomainToMediaDomain()
//    ),
//    tvRelatedToUseCase = TVRelatedToUseCase.Default(lookup()),
//)
//
//val collectionComponent = CollectionComponent.Default(
//    saveMediaToCollection = SaveMediaToCollectionUseCase.Default(
//        collectionRepo = lookup(),
//        movieDetails = MovieDetailsUseCase.Default(lookup()),
//        tvDetails = TVDetailsUseCase.Default(lookup())
//    ),
//    removeMediaFromCollection = RemoveMediaFromCollectionUseCase.Default(lookup()),
//    createCollection = CreateCollectionUseCase.Default(lookup()),
//    fetchAllCollections = FetchAllCollectionsUseCase.Default(lookup()),
//    fetchCollection = FetchCollectionUseCase.Default(lookup()),
//)
//
//return@lazy MLSearchViewModel(
//    searchComponent = searchComponent,
//    collectionComponent = collectionComponent,
//    mediaItemMapper = Mapper.DomainToUI(),
//    movieListMapper = ListMapper.Impl(Mapper.MovieEntity.DomainToUI()),
//    coroutineScope = viewModelScope,
//)
