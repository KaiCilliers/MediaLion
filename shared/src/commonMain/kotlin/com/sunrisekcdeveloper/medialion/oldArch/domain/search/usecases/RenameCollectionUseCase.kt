package com.sunrisekcdeveloper.medialion.oldArch.domain.search.usecases

import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title
import com.sunrisekcdeveloper.medialion.oldArch.repos.CollectionRepository

interface RenameCollectionUseCase {
    suspend operator fun invoke(oldCollectionName: Title, newCollectionName: Title)
    class Default(
        private val collectionRepo: CollectionRepository
    ) : RenameCollectionUseCase {
        override suspend fun invoke(oldCollectionName: Title, newCollectionName: Title) {
            // todo Title should never be blank
            if (newCollectionName.value.trim().isNotBlank()) {
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
}