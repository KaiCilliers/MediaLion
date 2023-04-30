package com.example.medialion.domain.components.search.usecases

import com.example.medialion.data.repos.TVRepository
import com.example.medialion.domain.models.TVShow
import kotlinx.coroutines.flow.Flow

interface TVRelatedToUseCase {
    operator fun invoke(id: Int): Flow<TVShow>
    class Default(
        private val tvRepo: TVRepository
    ) : TVRelatedToUseCase {
        override fun invoke(id: Int): Flow<TVShow> {
            return tvRepo.relatedTo(id)
        }
    }
}