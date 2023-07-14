package com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.getOr
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models.MediaRequirementsFactory
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.repo.TitledMediaRepository
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.MediaWithTitle
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.repos.CollectionRepositoryNew
import io.ktor.utils.io.printStack

interface FetchSuggestedMediaUseCase {
    suspend operator fun invoke(): Result<MediaWithTitle, FetchSuggestedMediaError>

    class Def(
        private val mediaRequirementsFactory: MediaRequirementsFactory,
        private val titledMediaRepository: TitledMediaRepository,
        private val collectionRepository: CollectionRepositoryNew,
    ) : FetchSuggestedMediaUseCase {

        override suspend fun invoke(): Result<MediaWithTitle, FetchSuggestedMediaError> {
            return runCatching {
                val requirements = mediaRequirementsFactory.suggestedMediaRequirements()
                val excludedIds = collectionRepository
                    .all()
                    .getOr(emptyList())
                    .flatMap { it.media() }
                    .map { it.identifier() }
                val titledMedia = titledMediaRepository.withRequirement(requirements.copy(withoutMedia = excludedIds))
                titledMedia
            }
                .mapError {Exception(it).printStack()
                    FailedToFetchFeatureMedia }
        }
    }
}

sealed interface FetchSuggestedMediaError
object FailedToFetchFeatureMedia : FetchSuggestedMediaError