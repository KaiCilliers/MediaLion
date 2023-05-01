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
            throw Exception("HA")
            return remoteDataSource.movieDetails(id)
        }

        override fun recommendationsForMovie(id: Int): Flow<Movie> {
            return remoteDataSource.recommendationsForMovie(id)
        }

        override fun popularMovies(): Flow<Movie> {
            return remoteDataSource.popularMovies()
        }

        override fun search(query: String): Flow<Movie> {
            throw Exception("FLOW MAN")
            return remoteDataSource.search(query)
        }

        override fun moviesRelatedTo(id: Int): Flow<Movie> {
            return remoteDataSource.similarForMovie(id)
        }
    }

    class Strict(
        private val origin: MovieRepository
    ) : MovieRepository {
        override suspend fun movieDetails(id: Int): MovieDetail {
            return try {
                origin.movieDetails(id)
            } catch (e: Exception) {
                throw Exception("my message", e)
            }
        }

        override fun recommendationsForMovie(id: Int): Flow<Movie> {
            return try {
                origin.recommendationsForMovie(id)
            } catch (e: Exception) {
                throw Exception("my message", e)
            }
        }

        override fun popularMovies(): Flow<Movie> {
            return try {
                origin.popularMovies()
            } catch (e: Exception) {
                throw Exception("my message", e)
            }
        }

        override fun search(query: String): Flow<Movie> {
            return try {
                origin.search(query)
            } catch (e: Exception) {
                throw Exception("my message", e)
            }
        }

        override fun moviesRelatedTo(id: Int): Flow<Movie> {
            return try {
                origin.moviesRelatedTo(id)
            } catch (e: Exception) {
                throw Exception("my message", e)
            }
        }
    }

}