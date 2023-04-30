package com.example.medialion.data.repos

import com.example.medialion.data.datasource.MovieRemoteDataSource
import com.example.medialion.domain.models.Movie
import com.example.medialion.domain.models.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun movieDetails(id: Int): MovieDetail
    fun recommendationsForMovie(id: Int): Flow<Movie>
    fun popularMovies(): Flow<Movie>
    fun search(query: String): Flow<Movie>
    fun moviesRelatedTo(id: Int): Flow<Movie>

    class Default(
        private val remoteDataSource: MovieRemoteDataSource
    ) : MovieRepository {
        override suspend fun movieDetails(id: Int): MovieDetail {
            return remoteDataSource.movieDetails(id)
        }

        override fun recommendationsForMovie(id: Int): Flow<Movie> {
            return remoteDataSource.recommendationsForMovie(id)
        }

        override fun popularMovies(): Flow<Movie> {
            return remoteDataSource.popularMovies()
        }

        override fun search(query: String): Flow<Movie> {
            return remoteDataSource.search(query)
        }

        override fun moviesRelatedTo(id: Int): Flow<Movie> {
            return remoteDataSource.similarForMovie(id)
        }
    }
}