package com.sunrisekcdeveloper.medialion.oldArch.domain.search.usecases

import com.sunrisekcdeveloper.medialion.oldArch.domain.entities.CollectionWithMedia
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title
import com.sunrisekcdeveloper.medialion.oldArch.repos.CollectionRepository
import kotlinx.coroutines.flow.Flow

interface FetchCollectionUseCase {
    operator fun invoke(collectionName: Title): Flow<CollectionWithMedia>
    class Default(
        private val collectionRepo: CollectionRepository,
    ) : FetchCollectionUseCase {
        override fun invoke(collectionName: Title): Flow<CollectionWithMedia> {
            return collectionRepo.getCollection(collectionName.value)
        }
    }
}