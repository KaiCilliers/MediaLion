package com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.repos

import com.sunrisekcdeveloper.medialion.domain.value.Title
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.newarch.components.shared.utils.ForcedException
import com.sunrisekcdeveloper.medialion.newarch.components.shared.utils.wrapInList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

interface CollectionRepositoryNew {
    suspend fun observe(): Flow<List<CollectionNew>>
    suspend fun all(): List<CollectionNew>
    suspend fun collection(title: Title): List<CollectionNew>
    suspend fun upsert(collection: CollectionNew)
    suspend fun delete(collection: CollectionNew)

    class Fake : CollectionRepositoryNew {

        private var cache: Set<CollectionNew> = mutableSetOf(CollectionNew.Def("Favorite"))
            set(value) {
                cacheFlow.update { value }
                field = value
            }

        var forceFailure = false
        private val cacheFlow = MutableStateFlow<Set<CollectionNew>>(cache)

        fun clearCache() {
            cache.toMutableSet().run {
                clear()
                cache = this
            }
        }

        override suspend fun observe(): Flow<List<CollectionNew>> {
            if (forceFailure) throw ForcedException()
            return cacheFlow.map { it.toList() }
        }

        override suspend fun all(): List<CollectionNew> {
            if (forceFailure) throw ForcedException()
            return cache.toList()
        }

        override suspend fun collection(title: Title): List<CollectionNew> {
            val searchResults = cache
                .find { it.title() == title }
            return when (searchResults != null) {
                true -> searchResults.wrapInList()
                false -> emptyList()
            }
        }

        override suspend fun upsert(collection: CollectionNew) {
            if (forceFailure) throw ForcedException()
            cache.toMutableSet().run {
                remove(collection)
                add(collection)
                cache = this
            }
        }

        override suspend fun delete(collection: CollectionNew) {
            if (forceFailure) throw ForcedException()
            cache.toMutableSet().run {
                remove(collection)
                cache = this
            }
        }
    }
}

