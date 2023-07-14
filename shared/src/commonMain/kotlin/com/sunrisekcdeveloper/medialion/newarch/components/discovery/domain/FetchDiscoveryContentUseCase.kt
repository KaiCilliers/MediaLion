package com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models.DiscoveryPage
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models.MediaRequirements
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.repo.MediaRequirementsRepository
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.repo.TitledMediaRepository
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.MediaWithTitle
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.TitledMediaList

interface FetchDiscoveryContentUseCase {
    suspend operator fun invoke(page: DiscoveryPage): Result<TitledMediaList, FetchDiscoveryContentError>

    class D (
        private val mediaRequirementsRepo: MediaRequirementsRepository,
        private val titledMediaRepo: TitledMediaRepository
    ) : FetchDiscoveryContentUseCase {
        override suspend fun invoke(page: DiscoveryPage): Result<TitledMediaList, FetchDiscoveryContentError> {
            return runCatching {
                val requirements: List<MediaRequirements> = mediaRequirementsRepo.getForPage(page)
                val allMedia: List<MediaWithTitle> = requirements.map {
                    val titledMedia: MediaWithTitle = titledMediaRepo.withRequirement(it)
                    titledMedia
                }
                TitledMediaList.Def(allMedia)
            }.mapError { FailureToFetchDiscContent }
        }
    }
}

sealed interface FetchDiscoveryContentError
object FailureToFetchDiscContent : FetchDiscoveryContentError

