package com.example.medialion.domain.search.usecases

import com.example.medialion.domain.entities.Movie
import com.example.medialion.domain.value.ID
import com.example.medialion.repos.MovieRepository

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

