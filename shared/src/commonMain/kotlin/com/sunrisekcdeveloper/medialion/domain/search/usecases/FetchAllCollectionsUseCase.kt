package com.sunrisekcdeveloper.medialion.domain.search.usecases

import com.sunrisekcdeveloper.medialion.domain.entities.Collection
import com.sunrisekcdeveloper.medialion.repos.CollectionRepository
import kotlinx.coroutines.flow.Flow

interface FetchAllCollectionsUseCase {
    operator fun invoke(): Flow<List<Collection>>
    class Default(
        private val collectionRepo: CollectionRepository,
    ) : FetchAllCollectionsUseCase {
        override fun invoke(): Flow<List<Collection>> {
            return collectionRepo.allCollections()
        }
    }
}