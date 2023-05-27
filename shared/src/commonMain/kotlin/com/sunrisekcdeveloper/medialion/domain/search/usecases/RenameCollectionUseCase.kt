package com.sunrisekcdeveloper.medialion.domain.search.usecases

import com.sunrisekcdeveloper.medialion.domain.value.Title
import com.sunrisekcdeveloper.medialion.repos.CollectionRepository

interface RenameCollectionUseCase {
    suspend operator fun invoke(oldCollectionName: Title, newCollectionName: Title)
    class Default(
        private val collectionRepo: CollectionRepository
    ) : RenameCollectionUseCase {
        override suspend fun invoke(oldCollectionName: Title, newCollectionName: Title) {
            collectionRepo.renameCollection(oldCollectionName, newCollectionName)
        }
    }
}