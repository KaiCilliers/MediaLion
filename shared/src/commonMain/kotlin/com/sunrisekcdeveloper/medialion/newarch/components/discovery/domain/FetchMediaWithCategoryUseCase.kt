package com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models.MediaCategory
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models.MediaRequirementsFactory
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.repo.TitledMediaRepository
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.MediaWithTitle
import io.ktor.utils.io.printStack

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
                .mapError {Exception(it).printStack()
                    FetchMediaWithCategoryFailure }
        }
    }
}

interface FetchMediaWithCategoryError
object FetchMediaWithCategoryFailure : FetchMediaWithCategoryError