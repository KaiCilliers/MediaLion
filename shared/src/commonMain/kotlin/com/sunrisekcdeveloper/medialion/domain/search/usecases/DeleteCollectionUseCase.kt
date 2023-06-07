package com.sunrisekcdeveloper.medialion.domain.search.usecases

import com.sunrisekcdeveloper.medialion.domain.value.Title
import com.sunrisekcdeveloper.medialion.repos.CollectionRepository

interface DeleteCollectionUseCase {
    suspend operator fun invoke(collectionName: Title)
    class Default(
        private val collectionRepo: CollectionRepository
    ) : DeleteCollectionUseCase {
        override suspend fun invoke(collectionName: Title) {
            collectionRepo.deleteCollection(Title(collectionName.value.trim()))
        }
    }
}