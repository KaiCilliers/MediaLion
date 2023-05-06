package com.example.medialion.domain.search.usecases

import com.example.medialion.repos.MovieRepository
import com.example.medialion.domain.entities.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take

interface SuggestedMediaUseCase {
    operator fun invoke(): Flow<Movie>

    class Default(
        private val movieRepo: MovieRepository
    ) : SuggestedMediaUseCase {
        override operator fun invoke(): Flow<Movie> {
            return movieRepo.popularMovies().take(30)
        }
    }
}