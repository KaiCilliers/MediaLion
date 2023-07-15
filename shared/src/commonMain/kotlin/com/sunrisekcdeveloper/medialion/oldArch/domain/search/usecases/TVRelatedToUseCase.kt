package com.sunrisekcdeveloper.medialion.oldArch.domain.search.usecases

import com.sunrisekcdeveloper.medialion.oldArch.domain.entities.TVShow
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.ID
import com.sunrisekcdeveloper.medialion.oldArch.repos.TVRepository
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