package com.sunrisekcdeveloper.medialion.oldArch.domain.search.usecases

import com.sunrisekcdeveloper.medialion.oldArch.domain.entities.Movie
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.ID
import com.sunrisekcdeveloper.medialion.oldArch.repos.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take

interface MoviesRelatedToUseCase {
    operator fun invoke(id: ID): Flow<Movie>
    class Default(
        private val movieRepo: MovieRepository
    ) : MoviesRelatedToUseCase {
        override fun invoke(id: ID): Flow<Movie> {
            return movieRepo.moviesRelatedTo(id.value).take(30)
        }
    }
}