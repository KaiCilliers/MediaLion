package com.example.medialion.data.datasource

import com.example.medialion.data.models.MovieDetailResponse
import com.example.medialion.data.models.MovieListResponse
import com.example.medialion.data.clients.TMDBClient
import com.example.medialion.domain.models.Keyword
import com.example.medialion.domain.models.Movie
import com.example.medialion.domain.models.MovieDetail
import com.example.medialion.domain.mappers.ListMapper
import com.example.medialion.domain.mappers.Mapper
import com.example.medialion.domain.models.ResultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

interface MovieRemoteDataSource {
    suspend fun movieDetails(id: Int): MovieDetail
    suspend fun movieKeywords(id: Int): ResultOf<Keyword>
    fun recommendationsForMovie(id: Int): Flow<Movie>
    fun similarForMovie(id: Int): Flow<Movie>
    fun moviesNowInTheatres(): Flow<Movie>
    fun topRatedMovies(): Flow<Movie>
    fun popularMovies(): Flow<Movie>
    fun moviesComingToTheatres(): Flow<Movie>
    fun search(query: String): Flow<Movie>

    class MovieRemoteDataSourceTMDB(
        private val apiClient: TMDBClient,
        private val movieDetailMapper: Mapper<MovieDetailResponse, MovieDetail>,
        private val movieListMapper: ListMapper<MovieListResponse, Movie>,
    ) : MovieRemoteDataSource {
        override suspend fun movieDetails(id: Int): MovieDetail {
            val response = apiClient.movieDetails(id)
            return movieDetailMapper.map(response)
        }

        // todo add initial page parameter?
        override suspend fun movieKeywords(id: Int): ResultOf<Keyword> {
            TODO("")
        }

        override fun recommendationsForMovie(id: Int): Flow<Movie> = flow {
            var page = 1
            do {
                val response = apiClient.recommendationsForMovie(id, page++)
                emitAll(movieListMapper.map(response.results).asFlow())
            } while (page <= response.totalPages)
        }

        override fun search(query: String): Flow<Movie> = flow {
            var page = 1
            do {
                val response = apiClient.searchMovies(query, page++)
                emitAll(movieListMapper.map(response.results).asFlow())
            } while(page <= response.totalPages)
        }

        override fun similarForMovie(id: Int): Flow<Movie> = flow {
            var page = 1
            do {
                val response = apiClient.similarForMovie(id, page++)
                emitAll(movieListMapper.map(response.results).asFlow())
            } while(page <= response.totalPages)
        }

        override fun moviesNowInTheatres(): Flow<Movie> = flow {
            var page = 1
            do {
                val response = apiClient.moviesNowInTheatres(page++)
                emitAll(movieListMapper.map(response.results).asFlow())
            } while(page <= response.totalPages)
        }

        override fun topRatedMovies(): Flow<Movie> = flow {
            var page = 1
            do {
                val response = apiClient.topRatedMovies(page++)
                emitAll(movieListMapper.map(response.results).asFlow())
            } while(page <= response.totalPages)
        }

        override fun popularMovies(): Flow<Movie> = flow {
            var page = 1
            do {
                val response = apiClient.popularMovies(page++)
                emitAll(movieListMapper.map(response.results).asFlow())
            } while(page <= response.totalPages)
        }

        override fun moviesComingToTheatres(): Flow<Movie>  = flow {
            var page = 1
            do {
                val response = apiClient.moviesComingToTheatres(page++)
                emitAll(movieListMapper.map(response.results).asFlow())
            } while(page <= response.totalPages)
        }
    }
}