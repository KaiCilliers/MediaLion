package com.sunrisekcdeveloper.medialion.domain.search.usecases

import com.sunrisekcdeveloper.medialion.domain.entities.Movie
import com.sunrisekcdeveloper.medialion.domain.value.ID
import com.sunrisekcdeveloper.medialion.repos.MovieRepository

interface MovieDetailsUseCase {
    suspend operator fun invoke(id: ID): Result<Movie>
    class Default(
        private val movieRepo: MovieRepository
    ) : MovieDetailsUseCase {
        override suspend operator fun invoke(id: ID): Result<Movie> {
            return movieRepo.movieDetails(id)
        }
    }
}

