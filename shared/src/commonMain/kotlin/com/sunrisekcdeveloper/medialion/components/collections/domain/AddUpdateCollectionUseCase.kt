package com.sunrisekcdeveloper.medialion.components.collections.domain

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.repos.CollectionRepositoryNew

interface AddUpdateCollectionUseCase {
    suspend operator fun invoke(collection: CollectionNew): Result<Unit, com.sunrisekcdeveloper.medialion.components.collections.domain.AddUpdateCollectionError>

    class Def(
        private val collectionRepository: CollectionRepositoryNew
    ) : com.sunrisekcdeveloper.medialion.components.collections.domain.AddUpdateCollectionUseCase {
        override suspend fun invoke(collection: CollectionNew): Result<Unit, com.sunrisekcdeveloper.medialion.components.collections.domain.AddUpdateCollectionError> = runCatching {
            collectionRepository.upsert(collection)
        }
            .mapError { com.sunrisekcdeveloper.medialion.components.collections.domain.FailedToAddCollection }
    }
}

sealed interface AddUpdateCollectionError
object FailedToAddCollection : com.sunrisekcdeveloper.medialion.components.collections.domain.AddUpdateCollectionError