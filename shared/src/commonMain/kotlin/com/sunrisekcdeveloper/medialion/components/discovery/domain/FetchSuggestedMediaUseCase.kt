package com.sunrisekcdeveloper.medialion.components.discovery.domain

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.getOr
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaRequirementsFactory
import com.sunrisekcdeveloper.medialion.components.discovery.domain.repo.TitledMediaRepository
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.MediaWithTitle
import com.sunrisekcdeveloper.medialion.components.shared.domain.repos.CollectionRepositoryNew
import io.github.aakira.napier.Napier

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
                val excludedIds = runCatching {
                    collectionRepository
                        .all()
                        .flatMap { singleCollection -> singleCollection.media() }
                        .map { it.identifier() }
                }.getOr(emptyList())
                val titledMedia = titledMediaRepository.withRequirement(requirements.copy(withoutMedia = excludedIds))
                titledMedia
            }
                .mapError {
                    Napier.w(it) { "Failed to fetch suggested media" }
                    FailedToFetchFeatureMedia
                }
        }
    }

    class Fake : FetchSuggestedMediaUseCase {
        override suspend fun invoke(): Result<MediaWithTitle, FetchSuggestedMediaError> {
            return Ok(MediaWithTitle.Def("a"))
        }
    }
}

sealed interface FetchSuggestedMediaError
object FailedToFetchFeatureMedia : FetchSuggestedMediaError