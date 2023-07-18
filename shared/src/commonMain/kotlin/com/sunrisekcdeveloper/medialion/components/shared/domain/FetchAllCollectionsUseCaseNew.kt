package com.sunrisekcdeveloper.medialion.components.shared.domain

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.github.michaelbull.result.runCatching
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.repos.CollectionRepositoryNew
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

interface FetchAllCollectionsUseCaseNew {
    operator fun invoke(): Flow<Result<List<CollectionNew>, FetchAllCollectionsError>>

    class Default(
        private val collectionRepository: CollectionRepositoryNew
    ) : FetchAllCollectionsUseCaseNew {
        override fun invoke(): Flow<Result<List<CollectionNew>, FetchAllCollectionsError>> = flow {
            runCatching { collectionRepository.observe() }
                .onSuccess { collectionsFlow ->
                    emitAll(
                        collectionsFlow.map { collectionsList -> Ok(collectionsList) }
                    )
                }
                .onFailure { emit(Err(UnableToRetrieveCollections)) }
        }
    }

    class Fake : FetchAllCollectionsUseCaseNew {
        var forceFailure = false
        override fun invoke(): Flow<Result<List<CollectionNew>, FetchAllCollectionsError>> = flow {
                if (!forceFailure) {
                    emit(Ok(emptyList()))
                } else {
                    emit(Err(UnableToRetrieveCollections))
                }
            }
    }
}

sealed interface FetchAllCollectionsError
object UnableToRetrieveCollections : FetchAllCollectionsError