package com.sunrisekcdeveloper.medialion.components.shared.domain

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaCategory
import com.sunrisekcdeveloper.medialion.components.discovery.domain.repo.MediaCategoryRepository

interface FetchAllMediaCategoriesUseCase {
    suspend operator fun invoke(): Result<List<MediaCategory>, FetchAllMediaCategoriesError>

    class D(
        private val mediaCategoryRepository: MediaCategoryRepository
    ) : FetchAllMediaCategoriesUseCase {
        override suspend fun invoke(): Result<List<MediaCategory>, FetchAllMediaCategoriesError> {
            return runCatching {
                mediaCategoryRepository.all()
            }
                .mapError { UnableToFetchMediaCategories }
        }
    }
}

sealed interface FetchAllMediaCategoriesError
object UnableToFetchMediaCategories : FetchAllMediaCategoriesError