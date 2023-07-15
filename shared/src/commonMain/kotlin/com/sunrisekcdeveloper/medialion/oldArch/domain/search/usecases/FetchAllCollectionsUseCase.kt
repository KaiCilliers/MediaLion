package com.sunrisekcdeveloper.medialion.oldArch.domain.search.usecases

import com.sunrisekcdeveloper.medialion.oldArch.domain.entities.CollectionWithMedia
import com.sunrisekcdeveloper.medialion.oldArch.repos.CollectionRepository
import kotlinx.coroutines.flow.Flow

interface FetchAllCollectionsUseCase {
    operator fun invoke(): Flow<List<CollectionWithMedia>>
    class Default(
        private val collectionRepo: CollectionRepository,
    ) : FetchAllCollectionsUseCase {
        override fun invoke(): Flow<List<CollectionWithMedia>> {
            return collectionRepo.allCollections()
        }
    }
}