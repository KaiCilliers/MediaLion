package com.example.medialion.domain.components.saveToCollection

import com.example.medialion.data.datasource.MovieRemoteDataSource
import com.example.medialion.database.MediaLionDatabase
import com.example.medialion.domain.mappers.Mapper
import com.example.medialion.domain.models.MovieDetail
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import database.MovieEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

interface CollectionComponent {
    suspend fun addMovieToFavorites(movieId: Int)
    suspend fun removeMovieFromFavorites(movieId: Int)
    fun favoriteMovieIds(): Flow<List<Int>>
    fun allCollections(): Flow<List<Pair<String, List<Int>>>>
    suspend fun addMovieToCollection(collectionName: String, movieId: Int)
    suspend fun removeMovieFromCollection(collectionName: String, movieId: Int)
    suspend fun createCollection(collectionName: String)

    class Default(
        private val database: MediaLionDatabase,
        private val movieRemoteDataSource: MovieRemoteDataSource,
        private val movieEntityMapper: Mapper<MovieDetail, MovieEntity>,
    ) : CollectionComponent {
        override suspend fun addMovieToFavorites(movieId: Int) {
            addMovieToCollection("favorite", movieId)
        }

        override suspend fun removeMovieFromFavorites(movieId: Int) {
            removeMovieFromCollection("favorite", movieId)
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

        override suspend fun addMovieToCollection(collectionName: String, movieId: Int) {
            if (database.myCollectionQueries.findCollection(collectionName).executeAsOneOrNull() == null) {
                database.myCollectionQueries.insert(collectionName)
            }

            if (database.movieQueries.findMovie(movieId).executeAsOneOrNull() == null) {
                val response = movieRemoteDataSource.movieDetails(movieId)
                database.movieQueries.insert(movieEntityMapper.map(response))
            }

            database.collectionMoviesXREFQueries.insert(collectionName, movieId)
        }

        override suspend fun removeMovieFromCollection(collectionName: String, movieId: Int) {
            if (database.myCollectionQueries.findCollection(collectionName).executeAsOneOrNull() == null) {
                database.myCollectionQueries.insert(collectionName)
            }

            if (database.movieQueries.findMovie(movieId).executeAsOneOrNull() == null) {
                val response = movieRemoteDataSource.movieDetails(movieId)
                database.movieQueries.insert(movieEntityMapper.map(response))
            }

            database.collectionMoviesXREFQueries.removeMovieFromCollection(collectionName, movieId)
        }

        override suspend fun createCollection(collectionName: String) {
            database.myCollectionQueries.insert(collectionName)
        }
    }
}