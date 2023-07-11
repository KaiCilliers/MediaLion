package com.sunrisekcdeveloper.medialion.newarch.components.collections.domain

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.TitledMediaList
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.repos.CollectionRepositoryNew

interface FetchAllCollectionsUseCaseNew {
    suspend operator fun invoke(): Result<TitledMediaList, FetchAllCollectionsError>

    class Default(
        private val collectionRepository: CollectionRepositoryNew
    ) : FetchAllCollectionsUseCaseNew {
        override suspend fun invoke(): Result<TitledMediaList, FetchAllCollectionsError> {
            return collectionRepository
                .all()
                .mapError { UnableToRetrieveCollections }
                .map { collections ->
                    TitledMediaList.Def(collections.map { it.asMediaWithTitle() })
                }
        }
    }

    class Fake : FetchAllCollectionsUseCaseNew {
        var hinder = false
        override suspend fun invoke(): Result<TitledMediaList, FetchAllCollectionsError> {
            return if (!hinder) {
                Ok(TitledMediaList.Def(emptyList()))
            } else {
                Err(UnableToRetrieveCollections)
            }
        }
    }
}

sealed interface FetchAllCollectionsError
object UnableToRetrieveCollections : FetchAllCollectionsError