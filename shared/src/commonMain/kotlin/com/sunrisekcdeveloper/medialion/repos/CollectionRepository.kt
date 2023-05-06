package com.sunrisekcdeveloper.medialion.repos

import com.sunrisekcdeveloper.medialion.database.MediaLionDatabase
import com.sunrisekcdeveloper.medialion.clients.runReThrowable
import com.sunrisekcdeveloper.medialion.clients.suspendRunReThrowable
import com.sunrisekcdeveloper.medialion.data.DispatcherProvider
import com.sunrisekcdeveloper.medialion.domain.MediaType
import com.sunrisekcdeveloper.medialion.domain.entities.Collection
import com.sunrisekcdeveloper.medialion.domain.entities.MediaItem
import com.sunrisekcdeveloper.medialion.domain.entities.Movie
import com.sunrisekcdeveloper.medialion.domain.entities.TVShow
import com.sunrisekcdeveloper.medialion.domain.value.ID
import com.sunrisekcdeveloper.medialion.domain.value.Title
import com.sunrisekcdeveloper.medialion.mappers.ListMapper
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import database.MovieCache
import database.TVShowCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

interface CollectionRepository {
    suspend fun insertCollection(name: String)
    fun getCollection(name: String): Flow<Collection>
    fun allCollections(): Flow<List<Collection>>
    suspend fun addMediaToCollection(collection: String, mediaItem: MediaItem)
    suspend fun removeMediaFromCollection(collection: String, mediaId: ID, mediaType: MediaType)

    class Default(
        private val database: MediaLionDatabase,
        private val dispatcherProvider: DispatcherProvider,
        private val movieCacheToDomainList: ListMapper<MovieCache, Movie>,
        private val tvShowCacheToDomainList: ListMapper<TVShowCache, TVShow>,
        private val movieDomainToMediaDomain: ListMapper<Movie, MediaItem>,
        private val tvShowDomainToMediaDomain: ListMapper<TVShow, MediaItem>,
    ) : CollectionRepository {

        private val collectionQueries = database.tbl_collectionQueries

        override suspend fun insertCollection(name: String) {
            suspendRunReThrowable("Failed to inset a collection [name=$name]") {
                withContext(dispatcherProvider.io) {
                    collectionQueries.insert(name)
                }
            }
                .onFailure { throw it }
        }

        override fun getCollection(name: String): Flow<Collection> {
            return database.transactionWithResult {
                runReThrowable("Failed to fetch a collection from database [name=$name]") {

                    collectionQueries.insert(name)
                    val movies = database.xref_movie_collectionQueries.moviesFromCollection(name).asFlow().mapToList()
                    val tvShows = database.xref_tvshow_collectionQueries.tvShowsFromCollection(name).asFlow().mapToList()

                    combine(movies, tvShows) { sourceMovies, sourceTVShows ->
                        movieDomainToMediaDomain.map(movieCacheToDomainList.map(sourceMovies)) + tvShowDomainToMediaDomain.map(tvShowCacheToDomainList.map(sourceTVShows))
                            .sortedBy { it.id.value }
                    }
                        .flowOn(dispatcherProvider.io)
                        .map {
                            Collection(name = Title(name), contents = it)
                        }

                }
            }.getOrDefault(emptyFlow())
        }

        override fun allCollections(): Flow<List<Collection>> {
            return collectionQueries
                .fetchAll()
                .asFlow()
                .mapToList()
                .map { collectionNames ->
                    collectionNames.map { getCollection(it).first() }
                }
        }

        override suspend fun addMediaToCollection(collection: String, mediaItem: MediaItem) {
            withContext(dispatcherProvider.io) {
                when(mediaItem) {
                    is Movie -> {
                        database.transaction {
                            collectionQueries.insert(collection)
                            database.xref_movie_collectionQueries.addToCollection(collection, mediaItem.id.value)
                        }
                    }
                    is TVShow -> {
                        database.transaction {
                            collectionQueries.insert(collection)
                            database.xref_tvshow_collectionQueries.addToCollection(collection, mediaItem.id.value)
                        }
                    }
                }
            }
        }

        override suspend fun removeMediaFromCollection(collection: String, mediaId: ID, mediaType: MediaType) {
            withContext(dispatcherProvider.io) {
                when(mediaType) {
                    MediaType.MOVIE -> {
                        database.transaction {
                            collectionQueries.insert(collection)
                            database.xref_movie_collectionQueries.removeFromCollection(collection, mediaId.value)
                        }
                    }
                    MediaType.TV -> {
                        database.transaction {
                            collectionQueries.insert(collection)
                            database.xref_tvshow_collectionQueries.removeFromCollection(collection, mediaId.value)
                        }
                    }
                }
            }
        }
    }
}