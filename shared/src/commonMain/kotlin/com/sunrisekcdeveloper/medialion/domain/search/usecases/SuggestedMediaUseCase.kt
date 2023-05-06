package com.sunrisekcdeveloper.medialion.domain.search.usecases

import com.sunrisekcdeveloper.medialion.repos.MovieRepository
import com.sunrisekcdeveloper.medialion.domain.entities.Movie
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