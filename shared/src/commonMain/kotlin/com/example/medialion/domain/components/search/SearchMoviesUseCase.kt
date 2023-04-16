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

}