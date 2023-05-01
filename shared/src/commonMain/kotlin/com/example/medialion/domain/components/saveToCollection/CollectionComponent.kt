package com.example.medialion.domain.components.saveToCollection

import com.example.medialion.data.datasource.MovieRemoteDataSource
import com.example.medialion.data.models.MovieDetailResponse
import com.example.medialion.database.MediaLionDatabase
import com.example.medialion.domain.mappers.Mapper
import com.example.medialion.domain.models.Movie
import com.example.medialion.domain.models.MovieDetail
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import database.MovieDetailEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

interface CollectionComponent {
    suspend fun addMovieToFavorites(movieId: Int)
    suspend fun removeMovieFromFavorites(movieId: Int)
    fun favoriteMovieIds(): Flow<List<Int>>

    class Default(
        private val database: MediaLionDatabase,
        private val movieRemoteDataSource: MovieRemoteDataSource,
        private val movieEntityMapper: Mapper<MovieDetail, MovieDetailEntity>,
    ) : CollectionComponent {
        override suspend fun addMovieToFavorites(movieId: Int) {
            if (database.myCollectionQueries.findCollection("favorite").executeAsOneOrNull() == null) {
                database.myCollectionQueries.insert("favorite")
            }

            if (database.movieQueries.findMovie(movieId).executeAsOneOrNull() == null) {
                val response = movieRemoteDataSource.movieDetails(movieId)
                database.movieDetailQueries.insert(movieEntityMapper.map(response))
            }

            database.collectionMoviesXREFQueries.insert("favorite", movieId)
        }

        override suspend fun removeMovieFromFavorites(movieId: Int) {
            if (database.myCollectionQueries.findCollection("favorite").executeAsOneOrNull() == null) {
                database.myCollectionQueries.insert("favorite")
            }

            if (database.movieQueries.findMovie(movieId).executeAsOneOrNull() == null) {
                val response = movieRemoteDataSource.movieDetails(movieId)
                database.movieDetailQueries.insert(movieEntityMapper.map(response))
            }

            database.collectionMoviesXREFQueries.removeMovieFromCollection(movieId)
        }

        override fun favoriteMovieIds(): Flow<List<Int>> {
            return database.collectionMoviesXREFQueries.moviesFromList("favorite")
                .asFlow()
                .mapToList()
                .map { it.map { it.id } }
        }
    }
}