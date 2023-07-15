package com.sunrisekcdeveloper.medialion.components.collections.domain

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.repos.CollectionRepositoryNew

interface InsertDefaultCollectionsUseCase {
    suspend operator fun invoke(): Result<Unit, InsertDefaultCollectionsError>

    class Def(
        private val collectionRepository: CollectionRepositoryNew
    ) : InsertDefaultCollectionsUseCase {
        override suspend fun invoke(): Result<Unit, InsertDefaultCollectionsError> = runCatching {
            val defaultCollections = listOf(
                CollectionNew.Def("Favorites")
            )
            defaultCollections.map {
                collectionRepository.upsert(it)
            }
            Unit
        }
            .mapError { FailedToInsertAllCollections }
    }

    class Fake : InsertDefaultCollectionsUseCase {
        override suspend fun invoke(): Result<Unit, InsertDefaultCollectionsError> {
            return Ok(Unit)
        }
    }
}

sealed interface InsertDefaultCollectionsError
object FailedToInsertAllCollections : InsertDefaultCollectionsError