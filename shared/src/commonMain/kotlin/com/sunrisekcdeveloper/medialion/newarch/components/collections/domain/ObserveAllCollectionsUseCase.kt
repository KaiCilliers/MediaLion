package com.sunrisekcdeveloper.medialion.newarch.components.collections.domain

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.TitledMediaList
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.repos.CollectionRepositoryNew
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

@OptIn(ExperimentalCoroutinesApi::class)
interface ObserveAllCollectionsUseCase {
    suspend operator fun invoke(): Flow<Result<TitledMediaList, ObserveAllCollectionsError>>

    class Def(
        private val collectionRepository: CollectionRepositoryNew
    ) : ObserveAllCollectionsUseCase {
        override suspend fun invoke(): Flow<Result<TitledMediaList, ObserveAllCollectionsError>> {
            return collectionRepository
                .observe()
                .flatMapLatest { result ->
                    flow {
                        emit(
                            result
                                .map { TitledMediaList.Def(it.map { it.asMediaWithTitle() }) }
                                .mapError { UnableToObserveCollections }
                        )
                    }
                }
        }
    }
}

sealed interface ObserveAllCollectionsError
object UnableToObserveCollections : ObserveAllCollectionsError