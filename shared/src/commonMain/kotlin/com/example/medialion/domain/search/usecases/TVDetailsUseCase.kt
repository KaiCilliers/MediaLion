package com.example.medialion.domain.search.usecases

import com.example.medialion.domain.entities.TVShow
import com.example.medialion.domain.value.ID
import com.example.medialion.repos.TVRepository

interface TVDetailsUseCase {
    suspend operator fun invoke(id: ID): Result<TVShow>
    class Default(
        private val tvRepo: TVRepository
    ) : TVDetailsUseCase {
        override suspend operator fun invoke(id: ID): Result<TVShow> {
            return tvRepo.tvDetails(id)
        }
    }
}