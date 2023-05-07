package com.sunrisekcdeveloper.medialion.domain.search.usecases

import com.sunrisekcdeveloper.medialion.domain.MediaType
import com.sunrisekcdeveloper.medialion.domain.value.ID
import com.sunrisekcdeveloper.medialion.domain.value.Title
import com.sunrisekcdeveloper.medialion.repos.CollectionRepository

interface RemoveMediaFromCollectionUseCase {
    suspend operator fun invoke(collectionName: Title, mediaId: ID, mediaType: MediaType)
    class Default(
        private val collectionRepo: CollectionRepository,
    ) : RemoveMediaFromCollectionUseCase {
        override suspend fun invoke(collectionName: Title, mediaId: ID, mediaType: MediaType) {
            collectionRepo.removeMediaFromCollection(collectionName.value, mediaId, mediaType)
        }
    }
}