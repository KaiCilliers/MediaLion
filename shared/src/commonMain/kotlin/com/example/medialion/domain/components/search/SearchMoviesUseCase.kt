package com.example.medialion.domain.components.search

import com.example.medialion.data.extensions.map
import com.example.medialion.data.searchComponent.MediaResponse
import com.example.medialion.data.searchComponent.TMDBClient
import com.example.medialion.domain.mappers.Mapper
import com.example.medialion.domain.models.Movie
import com.example.medialion.domain.models.ResultOf
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface SearchMoviesUseCase {

    suspend fun searchMovies(query: String): ResultOf<List<Movie>>
    class Default(
        private val client: TMDBClient,
        private val dispatcher: CoroutineDispatcher,
        private val movieMapper: Mapper<MediaResponse, Movie>,
    ) : SearchMoviesUseCase {
        override suspend fun searchMovies(query: String) = withContext(dispatcher) {
            return@withContext when (val response = client.searchMovies(query)) {
                is ResultOf.Success -> response.map { it.results.map { item -> movieMapper.map(item) } }
                is ResultOf.Failure -> response
            }
        }
    }

    class Fake : SearchMoviesUseCase {
        var provideFailure: Boolean = false
        override suspend fun searchMovies(query: String): ResultOf<List<Movie>> {
            return if (provideFailure) {
                ResultOf.Failure("Force failure...", null)
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

