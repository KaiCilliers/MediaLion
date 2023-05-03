package com.example.medialion.domain.components.saveToCollection

import com.example.medialion.data.datasource.MovieRemoteDataSource
import com.example.medialion.data.datasource.TVRemoteDataSource
import com.example.medialion.database.MediaLionDatabase
import com.example.medialion.domain.mappers.Mapper
import com.example.medialion.domain.models.MediaType
import com.example.medialion.domain.models.MovieDetail
import com.example.medialion.domain.models.TVShowDetail
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import database.MovieEntity
import database.TvShowEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface CollectionComponent {
    suspend fun addMediaToFavorites(mediaId: Int, mediaType: MediaType)
    suspend fun removeMediaFromFavorites(mediaId: Int, mediaType: MediaType)
    fun favoriteMovieIds(): Flow<List<Int>>
    fun allCollections(): Flow<List<Pair<String, List<Int>>>>
    suspend fun addMediaToCollection(collectionName: String, mediaId: Int, mediaType: MediaType)
    suspend fun removeMediaFromCollection(collectionName: String, mediaId: Int, mediaType: MediaType)
    suspend fun createCollection(collectionName: String)

    class Default(
        private val database: MediaLionDatabase,
        private val movieRemoteDataSource: MovieRemoteDataSource,
        private val tvRemoteDataSource: TVRemoteDataSource,
        private val movieEntityMapper: Mapper<MovieDetail, MovieEntity>,
        private val tvEntityMapper: Mapper<TVShowDetail, TvShowEntity>,
    ) : CollectionComponent {
        override suspend fun addMediaToFavorites(mediaId: Int, mediaType: MediaType) {
            addMediaToCollection("favorite", mediaId, mediaType)
        }

        override suspend fun removeMediaFromFavorites(mediaId: Int, mediaType: MediaType) {
            removeMediaFromCollection("favorite", mediaId, mediaType)
        }

        override fun favoriteMovieIds(): Flow<List<Int>> {
            return database.collectionMoviesXREFQueries.moviesFromList("favorite")
                .asFlow()
                .mapToList()
                .map { it.map { it.id } }
        }

        override fun allCollections(): Flow<List<Pair<String, List<Int>>>> {
            val allCollections = database.myCollectionQueries.allCollections()
                .asFlow()
                .mapToList()

            return allCollections
                .map {
                    it.map { it to database.collectionMoviesXREFQueries.moviesFromList(it).executeAsList().map{ it.id } }
                }
        }

        override suspend fun addMediaToCollection(collectionName: String, mediaId: Int, mediaType: MediaType) {
            if (database.myCollectionQueries.findCollection(collectionName).executeAsOneOrNull() == null) {
                database.myCollectionQueries.insert(collectionName)
            }

            when(mediaType) {
                MediaType.MOVIE -> {
                    if (database.movieQueries.findMovie(mediaId).executeAsOneOrNull() == null) {
                        val response = movieRemoteDataSource.movieDetails(mediaId)
                        database.movieQueries.insert(movieEntityMapper.map(response))
                    }

                    database.collectionMoviesXREFQueries.insert(collectionName, mediaId)
                }
                MediaType.TV -> {
                    if (database.tvShowQueries.findTVShow(mediaId).executeAsOneOrNull() == null) {
                        val response = tvRemoteDataSource.tvShowDetails(mediaId)
                        database.tvShowQueries.insert(tvEntityMapper.map(response))
                    }
                    database.collectionTVShowsXREFQueries.insert(collectionName, mediaId)
                }
            }
        }

        override suspend fun removeMediaFromCollection(collectionName: String, mediaId: Int, mediaType: MediaType) {
            if (database.myCollectionQueries.findCollection(collectionName).executeAsOneOrNull() == null) {
                database.myCollectionQueries.insert(collectionName)
            }

            when (mediaType) {
                MediaType.MOVIE -> {
                    if (database.movieQueries.findMovie(mediaId).executeAsOneOrNull() == null) {
                        val response = movieRemoteDataSource.movieDetails(mediaId)
                        database.movieQueries.insert(movieEntityMapper.map(response))
                    }
                    database.collectionMoviesXREFQueries.removeMovieFromCollection(collectionName, mediaId)
                }
                MediaType.TV -> {
                    if (database.tvShowQueries.findTVShow(mediaId).executeAsOneOrNull() == null) {
                        val response = tvRemoteDataSource.tvShowDetails(mediaId)
                        database.tvShowQueries.insert(tvEntityMapper.map(response))
                    }
                    database.collectionTVShowsXREFQueries.removeTVFromCollection(collectionName, mediaId)
                }
            }
        }

        override suspend fun createCollection(collectionName: String) {
            database.myCollectionQueries.insert(collectionName)
        }
    }
}