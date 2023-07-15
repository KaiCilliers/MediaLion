package com.sunrisekcdeveloper.medialion.components.discovery.domain

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaCategory
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaRequirementsFactory
import com.sunrisekcdeveloper.medialion.components.discovery.domain.repo.TitledMediaRepository
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.MediaWithTitle

interface FetchMediaWithCategoryUseCase {
    suspend operator fun invoke(category: MediaCategory): Result<MediaWithTitle, FetchMediaWithCategoryError>

    class D(
        private val titledMediaRepo: TitledMediaRepository,
        private val mediaRequirementsFactory: MediaRequirementsFactory,
    ) : FetchMediaWithCategoryUseCase {
        override suspend fun invoke(category: MediaCategory): Result<MediaWithTitle, FetchMediaWithCategoryError> {
            return runCatching {
                val mediaRequirements = mediaRequirementsFactory.fromCategory(category)
                titledMediaRepo.withRequirement(mediaRequirements)
            }
                .mapError { FetchMediaWithCategoryFailure }
        }
    }
}

interface FetchMediaWithCategoryError
object FetchMediaWithCategoryFailure : FetchMediaWithCategoryError