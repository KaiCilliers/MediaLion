package com.example.medialion.domain.search.usecases

import com.example.medialion.repos.MovieRepository
import com.example.medialion.domain.entities.Movie
import com.example.medialion.domain.value.ID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take

interface MoviesRelatedToUseCase {
    operator fun invoke(id: ID): Flow<Movie>
    class Default(
        private val movieRepo: MovieRepository
    ) : MoviesRelatedToUseCase {
        override fun invoke(id: ID): Flow<Movie> {
            return movieRepo.recommendationsForMovie(id).take(30)
        }
    }
}