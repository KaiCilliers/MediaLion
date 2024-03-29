package com.sunrisekcdeveloper.medialion.components.shared.domain.repos

import com.sunrisekcdeveloper.medialion.components.shared.data.collection.CollectionEntityDto
import com.sunrisekcdeveloper.medialion.components.shared.data.collection.CollectionLocalDataSource
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.components.shared.utils.ForcedException
import com.sunrisekcdeveloper.medialion.components.shared.utils.mapList
import com.sunrisekcdeveloper.medialion.components.shared.utils.wrapInList
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title
import com.sunrisekcdeveloper.medialion.utils.mappers.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

interface CollectionRepositoryNew {
    fun observe(): Flow<List<CollectionNew>>
    suspend fun all(): List<CollectionNew>
    suspend fun collection(title: Title): List<CollectionNew>
    suspend fun upsert(collection: CollectionNew)
    suspend fun delete(collection: CollectionNew)

    // todo inject dispatcher
    class D(
        private val localDataSource: CollectionLocalDataSource,
        private val entityMapper: Mapper<CollectionEntityDto, CollectionNew>,
        private val domainMapper: Mapper<CollectionNew, CollectionEntityDto>
    ) : CollectionRepositoryNew {
        override fun observe(): Flow<List<CollectionNew>> {
            return localDataSource
                .observeCollections()
                .mapList { entity -> entityMapper.map(entity) }
        }

        override suspend fun all(): List<CollectionNew> {
            return localDataSource
                .observeCollections()
                .mapList { entity -> entityMapper.map(entity) }
                .first()
        }

        override suspend fun collection(title: Title): List<CollectionNew> {
            return localDataSource
                .findWithTitle(title)
                .map { entity -> entityMapper.map(entity) }
        }

        override suspend fun upsert(collection: CollectionNew) {
            domainMapper.map(collection).also { entity ->
                localDataSource.upsert(entity)
            }
        }

        override suspend fun delete(collection: CollectionNew) {
            localDataSource.delete(domainMapper.map(collection))
        }
    }

    class Fake : CollectionRepositoryNew {

        // used to pass the equality check from distinctUntilChanged implemented by state flow
        private data class Container(
            val list: List<CollectionNew>
        )

        private var cache: List<CollectionNew> = mutableListOf(CollectionNew.Def("Favorite"))
            set(value) {
                cacheFlow.tryEmit(Container(value.toList()))
                field = value
            }

        var forceFailure = false
        private val cacheFlow = MutableStateFlow<Container>(Container(cache.toList()))

        fun clearCache() {
            cache.toMutableList().run {
                clear()
                cache = this
            }
        }

        override fun observe(): Flow<List<CollectionNew>> {
            if (forceFailure) throw ForcedException()
            return cacheFlow.map { it.list.toList() }
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
            cache.toMutableList().run {
                val index = indexOfFirst { collection == it }
                if (index == -1) add(collection)
                else this[index] = CollectionNew.Def(title = collection.title(), media = collection.media())
                cache = this
            }
        }

        override suspend fun delete(collection: CollectionNew) {
            if (forceFailure) throw ForcedException()
            cache.toMutableList().run {
                remove(collection)
                cache = this
            }
        }
    }
}

