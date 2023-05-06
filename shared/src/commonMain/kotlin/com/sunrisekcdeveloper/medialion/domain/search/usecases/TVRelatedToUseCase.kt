package com.sunrisekcdeveloper.medialion.domain.search.usecases

import com.sunrisekcdeveloper.medialion.domain.entities.TVShow
import com.sunrisekcdeveloper.medialion.domain.value.ID
import com.sunrisekcdeveloper.medialion.repos.TVRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take

interface TVRelatedToUseCase {
    operator fun invoke(id: ID): Flow<TVShow>
    class Default(
        private val tvRepo: TVRepository
    ) : TVRelatedToUseCase {
        override fun invoke(id: ID): Flow<TVShow> {
            return tvRepo.relatedTo(id).take(30)
        }
    }
}