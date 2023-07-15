package com.sunrisekcdeveloper.medialion.oldArch.domain.search.usecases

import com.sunrisekcdeveloper.medialion.oldArch.domain.MediaType
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.ID
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title
import com.sunrisekcdeveloper.medialion.oldArch.repos.CollectionRepository

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