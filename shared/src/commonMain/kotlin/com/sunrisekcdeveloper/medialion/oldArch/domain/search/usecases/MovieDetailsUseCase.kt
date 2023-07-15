package com.sunrisekcdeveloper.medialion.oldArch.domain.search.usecases

import com.sunrisekcdeveloper.medialion.oldArch.domain.entities.Movie
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.ID
import com.sunrisekcdeveloper.medialion.oldArch.repos.MovieRepository

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