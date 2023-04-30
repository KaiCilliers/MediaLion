package com.example.medialion.domain.components.search.wip

import com.example.medialion.domain.components.search.wip.domain.Movie
import kotlinx.coroutines.flow.Flow

interface SuggestedMediaUseCase {
    operator fun invoke(movieId: Int): Flow<Movie>

    class Default(
        private val movieRepo: MovieRepository
    ) : SuggestedMediaUseCase {
        override operator fun invoke(movieId: Int): Flow<Movie> {
            return movieRepo.recommendationsForMovie(movieId)
        }
    }
}