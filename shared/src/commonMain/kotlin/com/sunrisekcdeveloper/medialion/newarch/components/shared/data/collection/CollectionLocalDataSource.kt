package com.sunrisekcdeveloper.medialion.newarch.components.shared.data.collection

import com.sunrisekcdeveloper.medialion.domain.value.Title
import com.sunrisekcdeveloper.medialion.newarch.components.shared.utils.ForcedException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.properties.Delegates

interface CollectionLocalDataSource {
    fun observeCollections(): Flow<List<CollectionEntityDto>>
    suspend fun upsert(entity: CollectionEntityDto)
    suspend fun findWithTitle(title: Title): List<CollectionEntityDto>
    suspend fun delete(collection: CollectionEntityDto)

    class Fake : CollectionLocalDataSource {

        var forceFailure = false

        private var emitNewValue = false
        private var collectionsCache: Set<CollectionEntityDto> by Delegates.observable(emptySet()) { _, _, value ->
            emitNewValue = true
        }

        fun clearCache() {
            collectionsCache.toMutableSet().run {
                clear()
                collectionsCache = this
            }
        }

        override fun observeCollections(): Flow<List<CollectionEntityDto>> {
            if (forceFailure) throw ForcedException()
            return flow {
                emit(collectionsCache.toList())
                while (true) {
                    if (emitNewValue) {
                        emit(collectionsCache.toList())
                        emitNewValue = false
                    }
                }
            }
        }

        override suspend fun upsert(entity: CollectionEntityDto) {
            if (forceFailure) throw ForcedException()
            collectionsCache.toMutableList().apply {
                val matchingEntityIndex = indexOfFirst { it == entity }
                if (matchingEntityIndex >= 0) {
                    removeAt(matchingEntityIndex)
                }
                add(entity)
                collectionsCache = this.toSet()
            }
        }

        override suspend fun findWithTitle(title: Title): List<CollectionEntityDto> {
            if (forceFailure) throw ForcedException()
            val results = mutableListOf<CollectionEntityDto>()
            val collection = collectionsCache.find { it.title == title }
            if (collection != null) {
                results.add(collection)
            }
            return results
        }

        override suspend fun delete(collection: CollectionEntityDto) {
            if (forceFailure) throw ForcedException()
            collectionsCache.toMutableSet().apply {
                remove(collection)
                collectionsCache = this.toSet()
            }
        }
    }
}