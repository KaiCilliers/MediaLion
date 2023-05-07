package com.sunrisekcdeveloper.medialion.domain.search

import com.sunrisekcdeveloper.medialion.domain.search.usecases.CreateCollectionUseCase
import com.sunrisekcdeveloper.medialion.domain.search.usecases.FetchAllCollectionsUseCase
import com.sunrisekcdeveloper.medialion.domain.search.usecases.FetchCollectionUseCase
import com.sunrisekcdeveloper.medialion.domain.search.usecases.RemoveMediaFromCollectionUseCase
import com.sunrisekcdeveloper.medialion.domain.search.usecases.SaveMediaToCollectionUseCase

interface CollectionComponent {

    val saveMediaToCollection: SaveMediaToCollectionUseCase
    val removeMediaFromCollection: RemoveMediaFromCollectionUseCase
    val createCollection: CreateCollectionUseCase
    val fetchAllCollections: FetchAllCollectionsUseCase
    val fetchCollection: FetchCollectionUseCase

    class Default(
        override val saveMediaToCollection: SaveMediaToCollectionUseCase,
        override val removeMediaFromCollection: RemoveMediaFromCollectionUseCase,
        override val createCollection: CreateCollectionUseCase,
        override val fetchAllCollections: FetchAllCollectionsUseCase,
        override val fetchCollection: FetchCollectionUseCase,
    ) : CollectionComponent

}