package com.sunrisekcdeveloper.medialion.components.shared.data.collection

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaCategory
import com.sunrisekcdeveloper.medialion.components.shared.data.mediaCategory.MediaCategoryEntityDto
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.ID
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.SingleMediaItem
import com.sunrisekcdeveloper.medialion.components.shared.utils.ForcedException
import com.sunrisekcdeveloper.medialion.components.shared.utils.mapList
import com.sunrisekcdeveloper.medialion.components.shared.utils.wrapInList
import com.sunrisekcdeveloper.medialion.database.MediaLionDatabase
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title
import com.sunrisekcdeveloper.medialion.utils.mappers.Mapper
import database.CategoryCache
import database.CollectionCache
import database.MovieCache
import database.TVShowCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlin.properties.Delegates

interface CollectionLocalDataSource {
    fun observeCollections(): Flow<List<CollectionEntityDto>>
    suspend fun upsert(entity: CollectionEntityDto)
    suspend fun findWithTitle(title: Title): List<CollectionEntityDto>
    suspend fun delete(collection: CollectionEntityDto)

    class D (
        private val db: MediaLionDatabase,
        private val movieCacheMapper: Mapper<MovieCache, SingleMediaItem.Movie>,
        private val tvCacheMapper: Mapper<TVShowCache, SingleMediaItem.TVShow>,
        private val movieDomainMapper: Mapper<SingleMediaItem.Movie, MovieCache>,
        private val tvDomainMapper: Mapper<SingleMediaItem.TVShow, TVShowCache>,
        private val mediaCategoryMapper: Mapper<CategoryCache, MediaCategoryEntityDto>,
        private val mediaCategoryDomainMapper: Mapper<MediaCategoryEntityDto, MediaCategory>,
    ) : CollectionLocalDataSource {

        private val collectionDao = db.tbl_collectionQueries
        private val moviesDao = db.tbl_movieQueries
        private val moviesXRefDao = db.xref_movie_collectionQueries
        private val tvShowsDao = db.tbl_tvshowQueries
        private val tvShowsXRefDao = db.xref_tvshow_collectionQueries
        private val categoriesDao = db.tbl_categoriesQueries

        override fun observeCollections(): Flow<List<CollectionEntityDto>> {
            return collectionDao
                .fetchAll()
                .asFlow()
                .mapToList()
                .map { it.sortedByDescending { it.createdAt.toLong() } }
                .mapList { collectionCache ->
                    val moviesInCollection = moviesXRefDao.moviesFromCollection(collectionCache.id).asFlow().mapToList()
                    val tvShowsInCollection = tvShowsXRefDao.tvShowsFromCollection(collectionCache.id).asFlow().mapToList()

                    combine(moviesInCollection, tvShowsInCollection) { movies, tvShows ->
                        movies.map {  movieCache ->
                            var movie = movieCacheMapper.map(movieCache)
                            movie = movie.copy(categories = categoriesFromIds(movieCache.genre_ids.map { it.toString() }))
                            movie
                        } + tvShows.map { tvCache ->
                            var tvShow = tvCacheMapper.map(tvCache)
                            tvShow = tvShow.copy(categories = categoriesFromIds(tvCache.genre_ids.map { it.toString() }))
                            tvShow
                        }
                            .sortedBy { it.id.uniqueIdentifier() }
                    }
                        .map { media ->
                            CollectionEntityDto(
                                id = ID.Def(collectionCache.id),
                                title = Title(collectionCache.name),
                                media = media
                            )
                        }
                        .first()
                }
        }

        override suspend fun upsert(entity: CollectionEntityDto) {
            db.transaction {
                val existingCollection = collectionDao
                    .fetchAll()
                    .executeAsList()
                    .find { it.id == entity.id.uniqueIdentifier() }
                if (existingCollection == null) {
                    collectionDao.insert(
                        id = entity.id.uniqueIdentifier(),
                        name = entity.title.value,
                        createdAt = Clock.System.now().toEpochMilliseconds().toString()
                    )
                } else {
                    collectionDao.renameCollection(entity.title.value, entity.id.uniqueIdentifier())
                }

                moviesXRefDao.removeAllFromCollection(entity.id.uniqueIdentifier())
                tvShowsXRefDao.removeAllFromCollection(entity.id.uniqueIdentifier())

                entity.media.forEach { item ->
                    when (item) {
                        is SingleMediaItem.Movie -> {
                            moviesDao.insert(movieDomainMapper.map(item))
                            moviesXRefDao.addToCollection(
                                collection_id = entity.id.uniqueIdentifier(),
                                movie_id = item.id.uniqueIdentifier()
                            )
                        }
                        is SingleMediaItem.TVShow -> {
                            tvShowsDao.insert(tvDomainMapper.map(item))
                            tvShowsXRefDao.addToCollection(
                                collection_id = entity.id.uniqueIdentifier(),
                                tvshow_id = item.id.uniqueIdentifier()
                            )
                        }
                    }
                }
            }
        }

        override suspend fun findWithTitle(title: Title): List<CollectionEntityDto> {
            val result = collectionDao
                .findCollection(title.value)
                .executeAsList()

            return if (result.isNotEmpty()) {
                val matchingCollection: CollectionCache = result.first()
                val moviesInCollection = moviesXRefDao.moviesFromCollection(matchingCollection.id).asFlow().mapToList()
                val tvShowsInCollection = tvShowsXRefDao.tvShowsFromCollection(matchingCollection.id).asFlow().mapToList()

                combine(moviesInCollection, tvShowsInCollection) { movies, tvShows ->
                    movies.map { movieCache ->
                        var movie = movieCacheMapper.map(movieCache)
                        movie = movie.copy(categories = categoriesFromIds(movieCache.genre_ids.map { it.toString() }))
                        movie
                    } + tvShows.map { tvCache ->
                        var tvShow = tvCacheMapper.map(tvCache)
                        tvShow = tvShow.copy(categories = categoriesFromIds(tvCache.genre_ids.map { it.toString() }))
                        tvShow
                    }
                        .sortedBy { it.id.uniqueIdentifier() }
                }
                    .map { media ->
                        CollectionEntityDto(
                            id = ID.Def(matchingCollection.id),
                            title = Title(matchingCollection.name),
                            media = media
                        )
                            .wrapInList()
                    }
                    .first()
            } else emptyList()
        }

        override suspend fun delete(collection: CollectionEntityDto) {
            collectionDao.delete(collection.id.uniqueIdentifier())
        }

        private fun categoriesFromIds(categoryIds: List<String>): List<MediaCategory> {
            return categoriesDao
                .all()
                .executeAsList()
                .map { mediaCategoryMapper.map(it) }
                .map { mediaCategoryDomainMapper.map(it) }
                .filter { categoryIds.contains(it.identifier().uniqueIdentifier()) }
        }
    }

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