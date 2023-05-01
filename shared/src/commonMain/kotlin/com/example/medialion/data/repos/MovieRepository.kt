package com.example.medialion.data.repos

import com.example.medialion.data.datasource.MovieRemoteDataSource
import com.example.medialion.data.models.MovieDetailResponse
import com.example.medialion.data.models.MovieListResponse
import com.example.medialion.domain.mappers.Mapper
import com.example.medialion.domain.models.Movie
import com.example.medialion.domain.models.MovieDetail
import com.example.medialion.local.MovieLocalDataSource
import database.MovieDetailEntity
import database.MovieEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList

interface MovieRepository {
    suspend fun movieDetails(id: Int): MovieDetail
    fun recommendationsForMovie(id: Int): Flow<Movie>
    fun popularMovies(): Flow<Movie>
    fun search(query: String): Flow<Movie>
    fun moviesRelatedTo(id: Int): Flow<Movie>

    class Default(
        private val remoteDataSource: MovieRemoteDataSource,
        private val localDataSource: MovieLocalDataSource,
        private val mapper: Mapper<MovieDetail, MovieDetailEntity>,
        private val movieDomainToEntityMapper: Mapper<Movie, MovieEntity>,
    ) : MovieRepository {
        override suspend fun movieDetails(id: Int): MovieDetail {
            val entity = localDataSource.movieDetails(id)
            if(entity == null) {
                val response = remoteDataSource.movieDetails(id)
                localDataSource.addDetailedMovie(mapper.map(response))
            }
            val ent = localDataSource.movieDetails(id)
            return ent!!
        }

        override fun recommendationsForMovie(id: Int): Flow<Movie> {
            return remoteDataSource.recommendationsForMovie(id)
        }

        override fun popularMovies(): Flow<Movie> {
            return remoteDataSource.popularMovies()
                .onEach { localDataSource.addMovieToList("popular", movieDomainToEntityMapper.map(it)) }
                .catch { emitAll(localDataSource.moviesForList("popular")) }
        }

        override fun search(query: String): Flow<Movie> {
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