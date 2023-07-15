package com.sunrisekcdeveloper.medialion.components.collections.domain

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.repos.CollectionRepositoryNew

interface DeleteCollectionUseCaseNew {
    suspend operator fun invoke(collection: CollectionNew): Result<Unit, com.sunrisekcdeveloper.medialion.components.collections.domain.DeleteCollectionError>

    class Def(
        private val collectionRepository: CollectionRepositoryNew,
    ) : com.sunrisekcdeveloper.medialion.components.collections.domain.DeleteCollectionUseCaseNew {
        override suspend fun invoke(collection: CollectionNew): Result<Unit, com.sunrisekcdeveloper.medialion.components.collections.domain.DeleteCollectionError> = runCatching {
            collectionRepository.delete(collection)
        }
            .mapError { com.sunrisekcdeveloper.medialion.components.collections.domain.FailedToDeleteCollection }
    }

}

sealed interface DeleteCollectionError
object FailedToDeleteCollection : com.sunrisekcdeveloper.medialion.components.collections.domain.DeleteCollectionError
object CollectionDoesNotExist : com.sunrisekcdeveloper.medialion.components.collections.domain.DeleteCollectionError
