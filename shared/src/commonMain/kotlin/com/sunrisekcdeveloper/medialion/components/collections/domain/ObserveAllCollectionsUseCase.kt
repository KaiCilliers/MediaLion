package com.sunrisekcdeveloper.medialion.components.collections.domain

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.TitledMediaList
import com.sunrisekcdeveloper.medialion.components.shared.domain.repos.CollectionRepositoryNew
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface ObserveAllCollectionsUseCase {
    suspend operator fun invoke(): Result<Flow<TitledMediaList>, ObserveAllCollectionsError>

    class Def(
        private val collectionRepository: CollectionRepositoryNew
    ) : ObserveAllCollectionsUseCase {
        override suspend fun invoke(): Result<Flow<TitledMediaList>, ObserveAllCollectionsError> = runCatching {
            collectionRepository
                .observe()
                .map { collections ->
                    TitledMediaList.Def(content = collections.map { it.asMediaWithTitle() })
                }
        }
            .mapError { UnableToObserveCollections }
    }
}

sealed interface ObserveAllCollectionsError
object UnableToObserveCollections : ObserveAllCollectionsError