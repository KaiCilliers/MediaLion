package com.sunrisekcdeveloper.medialion.domain.search.usecases

import com.sunrisekcdeveloper.medialion.domain.value.Title
import com.sunrisekcdeveloper.medialion.repos.CollectionRepository

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