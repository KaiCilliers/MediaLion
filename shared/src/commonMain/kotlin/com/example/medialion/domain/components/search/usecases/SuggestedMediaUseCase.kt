package com.example.medialion.domain.components.search.usecases

import com.example.medialion.data.repos.MovieRepository
import com.example.medialion.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface SuggestedMediaUseCase {
    operator fun invoke(): Flow<Movie>

    class Default(
        private val movieRepo: MovieRepository
    ) : SuggestedMediaUseCase {
        override operator fun invoke(): Flow<Movie> {
            return movieRepo.popularMovies()
        }
    }
}