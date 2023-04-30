package com.example.medialion.domain.components.search.wip

import com.example.medialion.data.models.MovieDetailResponse
import com.example.medialion.data.models.MovieListResponse
import com.example.medialion.data.searchComponent.TMDBClient
import com.example.medialion.domain.components.search.wip.domain.Keyword
import com.example.medialion.domain.components.search.wip.domain.Movie
import com.example.medialion.domain.components.search.wip.domain.MovieDetail
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
    suspend fun similarForMovie(id: Int): Flow<Movie>
    suspend fun moviesNowInTheatres(): Flow<Movie>
    suspend fun topRatedMovies(): Flow<Movie>
    suspend fun popularMovies(): Flow<Movie>
    suspend fun moviesComingToTheatres(): Flow<Movie>

    class MovieRemoteDataSourceTMDB(
        private val apiClient: TMDBClient,
        private val movieDetailMapper: Mapper<MovieDetailResponse, MovieDetail>,
        private val movieMapper: Mapper<MovieListResponse, Movie>,
        private val movieListMapper: ListMapper<MovieListResponse, Movie>,
    ) : MovieRemoteDataSource {
        override suspend fun movieDetails(id: Int): MovieDetail {
            val response = apiClient.movieDetails(id)
            return movieDetailMapper.map(response)
        }

        // todo add initial page parameter?
        override suspend fun movieKeywords(id: Int): ResultOf<Keyword> {
//            var page = 1
//            val response = apiClient,
//            do {
//
//            } while ()
            TODO("")
        }

        override fun recommendationsForMovie(id: Int): Flow<Movie> = flow {
            var page = 1
            do {
                val response = apiClient.recommendationsForMovie(id, page++)
                emitAll(movieListMapper.map(response.results).asFlow())
            } while (page <= response.totalPages)
        }

        override suspend fun similarForMovie(id: Int): Flow<Movie> {
            TODO("Not yet implemented")
        }

        override suspend fun moviesNowInTheatres(): Flow<Movie> {
            TODO("Not yet implemented")
        }

        override suspend fun topRatedMovies(): Flow<Movie> {
            TODO("Not yet implemented")
        }

        override suspend fun popularMovies(): Flow<Movie> {
            TODO("Not yet implemented")
        }

        override suspend fun moviesComingToTheatres(): Flow<Movie> {
            TODO("Not yet implemented")
        }
    }
}

class MappingException(
    message: String? = null,
    cause: Throwable? = null,
): Exception(message, cause)