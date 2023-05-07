package com.sunrisekcdeveloper.medialion.domain.search.usecases

import com.sunrisekcdeveloper.medialion.domain.entities.Collection
import com.sunrisekcdeveloper.medialion.domain.value.Title
import com.sunrisekcdeveloper.medialion.repos.CollectionRepository
import kotlinx.coroutines.flow.Flow

interface FetchCollectionUseCase {
    operator fun invoke(collectionName: Title): Flow<Collection>
    class Default(
        private val collectionRepo: CollectionRepository,
    ) : FetchCollectionUseCase {
        override fun invoke(collectionName: Title): Flow<Collection> {
            return collectionRepo.getCollection(collectionName.value)
        }
    }
}