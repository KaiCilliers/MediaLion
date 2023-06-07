package com.sunrisekcdeveloper.medialion.domain.search.usecases

import com.sunrisekcdeveloper.medialion.domain.value.Title
import com.sunrisekcdeveloper.medialion.repos.CollectionRepository

interface RenameCollectionUseCase {
    suspend operator fun invoke(oldCollectionName: Title, newCollectionName: Title)
    class Default(
        private val collectionRepo: CollectionRepository
    ) : RenameCollectionUseCase {
        override suspend fun invoke(oldCollectionName: Title, newCollectionName: Title) {
            // trimming should occur in Repo layer - usecases are suppose to just coordinate tasks and delegates work
            // remove business logic
            // Title should contain the logic that it should be trimmed!
            // see more here --> https://itnext.io/domain-driven-android-building-a-model-which-makes-sense-badb774c606d
            /*
            Model should not only contain data but also business constraints, rules and behaviours.
             */
            collectionRepo.renameCollection(oldCollectionName, Title(newCollectionName.value.trim()))
        }
    }
}