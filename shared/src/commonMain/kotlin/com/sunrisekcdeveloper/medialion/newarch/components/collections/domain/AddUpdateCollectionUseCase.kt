package com.sunrisekcdeveloper.medialion.newarch.components.collections.domain

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapError
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.repos.CollectionRepositoryNew

interface AddUpdateCollectionUseCase {
    suspend operator fun invoke(collection: CollectionNew): Result<Unit, AddUpdateCollectionError>

    class Def(
        private val collectionRepository: CollectionRepositoryNew
    ) : AddUpdateCollectionUseCase {
        override suspend fun invoke(collection: CollectionNew): Result<Unit, AddUpdateCollectionError> {
            return collectionRepository.upsert(collection)
                .mapError { FailedToAddCollection }
                .also {
                    println("result is $it")
                }
        }
    }
}

sealed interface AddUpdateCollectionError
object FailedToAddCollection : AddUpdateCollectionError