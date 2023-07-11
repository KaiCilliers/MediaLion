package com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.repos

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import com.sunrisekcdeveloper.medialion.domain.value.Title
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.ApiCallError
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.DatabaseAccessError
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.LocalCacheError
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.ResourceDoesNotExist
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.UnexpectedApiCommunicationError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

interface CollectionRepositoryNew {
    suspend fun observe(): Flow<Result<List<CollectionNew>, LocalCacheError>>
    suspend fun all(): Result<List<CollectionNew>, ApiCallError>
    suspend fun collection(title: Title): Result<CollectionNew, LocalCacheError>
    suspend fun upsert(collection: CollectionNew): Result<Unit, LocalCacheError>
    suspend fun delete(collection: CollectionNew): Result<Unit, LocalCacheError>

    class Fake : CollectionRepositoryNew {

        private var cache: Set<CollectionNew> = mutableSetOf(CollectionNew.Def("Favorite"))
            set(value) {
                cacheFlow.update { Ok(value) }
                field = value
            }

        var forceFailure = false
            set(value) {
                exceptionsFlow.update { value }
                field = value
            }
        private val exceptionsFlow = MutableStateFlow(false)
        private val cacheFlow = MutableStateFlow<Result<Set<CollectionNew>, LocalCacheError>>(Ok(cache))

        fun clearCache() {
            cache.toMutableSet().run {
                clear()
                cache = this
            }
        }

        override suspend fun observe(): Flow<Result<List<CollectionNew>, LocalCacheError>> {
            return exceptionsFlow.combine(cacheFlow) { throwFailure, result ->
                if (throwFailure) {
                    Err(DatabaseAccessError)
                } else result.map { it.toList() }
            }
        }

        override suspend fun all(): Result<List<CollectionNew>, ApiCallError> {
            return if (forceFailure) {
                Err(UnexpectedApiCommunicationError)
            } else {
                Ok(cache.toList())
            }
        }

        override suspend fun collection(title: Title): Result<CollectionNew, LocalCacheError> {
            val searchResults = cache
                .find { it.title() == title }
            return when (searchResults != null) {
                true -> Ok(searchResults)
                false -> Err(ResourceDoesNotExist)
            }
        }

        override suspend fun upsert(collection: CollectionNew): Result<Unit, LocalCacheError> {
            return runCatching {
                if (forceFailure) throw Exception("Forced exception")
                cache.toMutableSet().run {
                    remove(collection)
                    add(collection)
                    cache = this
                }
            }
                .map { Unit }
                .mapError { DatabaseAccessError }
        }

        override suspend fun delete(collection: CollectionNew): Result<Unit, LocalCacheError> {
            return runCatching {
                if (forceFailure) throw Exception("Forced exception")
                cache.toMutableSet().run {
                    remove(collection)
                    cache = this
                }
            }
                .map { Unit }
                .mapError { DatabaseAccessError }
        }
    }
}

