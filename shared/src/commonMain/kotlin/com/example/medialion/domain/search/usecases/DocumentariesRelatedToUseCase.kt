package com.example.medialion.domain.search.usecases

import com.example.medialion.repos.TVRepository
import com.example.medialion.domain.entities.TVShow
import com.example.medialion.domain.value.ID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take

interface DocumentariesRelatedToUseCase {
    operator fun invoke(id: ID): Flow<TVShow>
    class Default(
        private val tvRepository: TVRepository
    ) : DocumentariesRelatedToUseCase {
        override fun invoke(id: ID): Flow<TVShow> {
            return tvRepository.withGenre(id).take(30)
        }
    }
}