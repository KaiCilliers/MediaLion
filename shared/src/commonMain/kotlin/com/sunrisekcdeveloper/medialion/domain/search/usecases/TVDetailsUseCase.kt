package com.sunrisekcdeveloper.medialion.domain.search.usecases

import com.sunrisekcdeveloper.medialion.domain.entities.TVShow
import com.sunrisekcdeveloper.medialion.domain.value.ID
import com.sunrisekcdeveloper.medialion.repos.TVRepository

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