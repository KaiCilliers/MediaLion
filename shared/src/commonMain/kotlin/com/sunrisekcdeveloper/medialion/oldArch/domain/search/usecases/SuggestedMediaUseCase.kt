package com.sunrisekcdeveloper.medialion.oldArch.domain.search.usecases

import com.sunrisekcdeveloper.medialion.oldArch.domain.entities.Movie
import com.sunrisekcdeveloper.medialion.oldArch.repos.MovieRepository
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