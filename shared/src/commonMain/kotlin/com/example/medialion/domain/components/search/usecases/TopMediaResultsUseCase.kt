package com.example.medialion.domain.components.search.usecases

import com.example.medialion.data.repos.MovieRepository
import com.example.medialion.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface TopMediaResultsUseCase {
    operator fun invoke(searchQuery: String): Flow<Movie>
    class Default(
        private val movieRepo: MovieRepository,
    ) : TopMediaResultsUseCase {
        override fun invoke(searchQuery: String): Flow<Movie> {
            return movieRepo.search(searchQuery)
        }
    }
}