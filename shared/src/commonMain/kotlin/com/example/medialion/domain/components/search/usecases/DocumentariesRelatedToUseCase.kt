package com.example.medialion.domain.components.search.usecases

import com.example.medialion.data.repos.TVRepository
import com.example.medialion.domain.models.TVShow
import kotlinx.coroutines.flow.Flow

interface DocumentariesRelatedToUseCase {
    operator fun invoke(id: Int): Flow<TVShow>
    class Default(
        private val tvRepository: TVRepository
    ) : DocumentariesRelatedToUseCase {
        override fun invoke(id: Int): Flow<TVShow> {
            return tvRepository.withGenre(id)
        }
    }
}