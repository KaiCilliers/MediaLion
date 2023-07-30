package com.sunrisekcdeveloper.medialion.components.discovery.domain

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
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

        // todo cache this in local storage and only fetch again when x hours have passed
        private val categoryCache: MutableMap<MediaCategory, MediaWithTitle> = mutableMapOf()

        override suspend fun invoke(category: MediaCategory): Result<MediaWithTitle, FetchMediaWithCategoryError> {
            return runCatching {
                val cachedCategory = categoryCache[category]
                if (cachedCategory != null) {
                    cachedCategory
                } else {
                    val mediaRequirements = mediaRequirementsFactory.fromCategory(category)
                    titledMediaRepo
                        .withRequirement(mediaRequirements)
                        .also { categoryCache[category] = it }
                }
            }
                .mapError { FetchMediaWithCategoryFailure }
        }
    }

    class Fake : FetchMediaWithCategoryUseCase {
        var forceFailure  = false
        override suspend fun invoke(category: MediaCategory): Result<MediaWithTitle, FetchMediaWithCategoryError> {
            return if (!forceFailure) {
                Ok(MediaWithTitle.Def("title #1"))
            } else {
                Err(FetchMediaWithCategoryFailure)
            }
        }
    }
}

interface FetchMediaWithCategoryError
object FetchMediaWithCategoryFailure : FetchMediaWithCategoryError