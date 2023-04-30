package com.example.medialion.domain.components.search.usecases

import com.example.medialion.data.repos.MovieRepository
import com.example.medialion.domain.models.MovieDetail

interface MovieDetailsUseCase {
    suspend operator fun invoke(id: Int): MovieDetail
    class Default(
        private val movieRepo: MovieRepository
    ) : MovieDetailsUseCase {
        override suspend operator fun invoke(id: Int): MovieDetail {
            return movieRepo.movieDetails(id)
        }
    }
}