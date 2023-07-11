package com.sunrisekcdeveloper.medialion.newarch.components.collections.domain

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.LocalCacheError
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.repos.CollectionRepositoryNew

interface InsertDefaultCollectionsUseCase {
    suspend operator fun invoke(): Result<Unit, InsertDefaultCollectionsError>

    class Def(
        private val collectionRepository: CollectionRepositoryNew
    ) : InsertDefaultCollectionsUseCase {
        override suspend fun invoke(): Result<Unit, InsertDefaultCollectionsError> {
            val defaultCollections = listOf(
                CollectionNew.Def("Favorites")
            )
            val insertResults = defaultCollections.map {
                collectionRepository.upsert(it)
            }
            return if (insertResults.filterIsInstance<Err<LocalCacheError>>().isNotEmpty()) {
                Err(FailedToInsertAllCollections)
            } else {
                Ok(Unit)
            }
        }
    }

    class Fake : InsertDefaultCollectionsUseCase {
        override suspend fun invoke(): Result<Unit, InsertDefaultCollectionsError> {
            return Ok(Unit)
        }
    }
}

sealed interface InsertDefaultCollectionsError
object FailedToInsertAllCollections : InsertDefaultCollectionsError