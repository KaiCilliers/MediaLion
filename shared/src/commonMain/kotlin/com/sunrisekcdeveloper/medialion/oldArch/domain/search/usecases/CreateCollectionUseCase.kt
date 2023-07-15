package com.sunrisekcdeveloper.medialion.oldArch.domain.search.usecases

import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title
import com.sunrisekcdeveloper.medialion.oldArch.repos.CollectionRepository

interface CreateCollectionUseCase {
    suspend operator fun invoke(collectionName: Title)
    class Default(
        private val collectionRepo: CollectionRepository,
    ) : CreateCollectionUseCase {
        override suspend fun invoke(collectionName: Title) {
            collectionRepo.insertCollection(collectionName.value)
        }
    }
}