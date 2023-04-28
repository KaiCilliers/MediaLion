package com.example.medialion.domain.components.search

import com.example.medialion.data.extensions.map
import com.example.medialion.data.models.MovieListResponse
import com.example.medialion.data.models.MultiTypeResponse
import com.example.medialion.data.searchComponent.TMDBClient
import com.example.medialion.domain.mappers.Mapper
import com.example.medialion.domain.models.Movie
import com.example.medialion.domain.models.ResultOf
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface RelatedMoviesUseCase {
    suspend fun relateMovies(movieId: Int): ResultOf<List<Movie>>

    class Default(
        private val client: TMDBClient,
        private val dispatcher: CoroutineDispatcher,
        private val movieMapper: Mapper<MovieListResponse, Movie>,
    ) : RelatedMoviesUseCase {
        override suspend fun relateMovies(movieId: Int) = withContext(dispatcher) {
            return@withContext when (val response = client.recommendationsForMovie(movieId)) {
                is ResultOf.Success -> response.map { it.results.map { item -> movieMapper.map(item) } }
                is ResultOf.Failure -> response
            }
        }
    }

    class Fake : RelatedMoviesUseCase {
        var provideFailure: Boolean = false
        override suspend fun relateMovies(movieId: Int): ResultOf<List<Movie>> {
            return if (provideFailure) {
                ResultOf.Failure("Forced failure...", null)
            } else {
                ResultOf.Success(listOf(
                    Movie(
                        backdropPath = null,
                        genreIds = listOf(),
                        id = 1965,
                        language = "interesset",
                        overview = "definitiones",
                        popularity = 2.3,
                        posterPath = null,
                        releaseDate = "suscipiantur",
                        title = "porta",
                        favorited = false
                    ),
                    Movie(
                        backdropPath = null,
                        genreIds = listOf(),
                        id = 5871,
                        language = "usu",
                        overview = "maiorum",
                        popularity = 6.7,
                        posterPath = null,
                        releaseDate = "saepe",
                        title = "habitasse",
                        favorited = false
                    )
                ))
            }
        }
    }
}

interface TopRatedMoviesUseCase {
    suspend fun topRatedMovies(): ResultOf<List<Movie>>

    class Default(
        private val client: TMDBClient,
        private val dispatcher: CoroutineDispatcher,
        private val movieMapper: Mapper<MovieListResponse, Movie>,
    ) : TopRatedMoviesUseCase {
        override suspend fun topRatedMovies(): ResultOf<List<Movie>> = withContext(dispatcher) {
            return@withContext when (val response = client.topRatedMovies()) {
                is ResultOf.Success -> response.map { it.results.map { item -> movieMapper.map(item) } }
                is ResultOf.Failure -> response
            }
        }
    }

    class Fake : TopRatedMoviesUseCase {
        var provideFailure: Boolean = false
        val movies = mutableListOf(
            Movie(
                backdropPath = null,
                genreIds = listOf(),
                id = 1965,
                language = "interesset",
                overview = "definitiones",
                popularity = 2.3,
                posterPath = null,
                releaseDate = "suscipiantur",
                title = "porta",
                favorited = false
            ),
            Movie(
                backdropPath = null,
                genreIds = listOf(),
                id = 5871,
                language = "usu",
                overview = "maiorum",
                popularity = 6.7,
                posterPath = null,
                releaseDate = "saepe",
                title = "habitasse",
                favorited = false
            )
        )
        override suspend fun topRatedMovies(): ResultOf<List<Movie>> {
            return if (provideFailure) {
                ResultOf.Failure("Forced failure...", null)
            } else {
                ResultOf.Success(movies)
            }
        }
    }
}