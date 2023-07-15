package com.sunrisekcdeveloper.medialion.oldArch.domain.search.usecases

import com.sunrisekcdeveloper.medialion.oldArch.domain.entities.TVShow
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.ID
import com.sunrisekcdeveloper.medialion.oldArch.repos.TVRepository
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