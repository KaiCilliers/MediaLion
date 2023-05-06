package com.example.medialion.domain.search.usecases

import com.example.medialion.domain.entities.TVShow
import com.example.medialion.domain.value.ID
import com.example.medialion.repos.TVRepository
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