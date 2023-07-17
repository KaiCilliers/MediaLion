package com.sunrisekcdeveloper.medialion.components.collections.domain

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.TitledMediaList
import com.sunrisekcdeveloper.medialion.components.shared.domain.repos.CollectionRepositoryNew

interface FetchAllCollectionsAsTitledMediaUseCase {
    suspend operator fun invoke(): Result<TitledMediaList, FetchAllCollectionsAsTitledMediaError>

    class Default(
        private val collectionRepository: CollectionRepositoryNew
    ) : FetchAllCollectionsAsTitledMediaUseCase {
        override suspend fun invoke(): Result<TitledMediaList, FetchAllCollectionsAsTitledMediaError> = runCatching {
            val collections = collectionRepository
                .all()
                .map { singleCollection -> singleCollection.asMediaWithTitle() }
            TitledMediaList.Def(collections)
        }
            .mapError { UnableToRetrieveCollections }
    }

    class Fake : FetchAllCollectionsAsTitledMediaUseCase {
        var hinder = false
        override suspend fun invoke(): Result<TitledMediaList, FetchAllCollectionsAsTitledMediaError> {
            return if (!hinder) {
                Ok(TitledMediaList.Def(emptyList()))
            } else {
                Err(UnableToRetrieveCollections)
            }
        }
    }
}

sealed interface FetchAllCollectionsAsTitledMediaError
object UnableToRetrieveCollections : FetchAllCollectionsAsTitledMediaError