package com.example.medialion.domain.components.search.usecases

import com.example.medialion.data.repos.MovieRepository
import com.example.medialion.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRelatedToUseCase {
    operator fun invoke(id: Int): Flow<Movie>
    class Default(
        private val movieRepo: MovieRepository
    ) : MoviesRelatedToUseCase {
        override fun invoke(id: Int): Flow<Movie> {
            return movieRepo.recommendationsForMovie(id)
        }
    }
}