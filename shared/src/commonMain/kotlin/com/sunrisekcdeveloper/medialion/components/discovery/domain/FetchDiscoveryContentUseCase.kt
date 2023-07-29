package com.sunrisekcdeveloper.medialion.components.discovery.domain

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.DiscoveryPage
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaRequirements
import com.sunrisekcdeveloper.medialion.components.discovery.domain.repo.MediaRequirementsRepository
import com.sunrisekcdeveloper.medialion.components.discovery.domain.repo.TitledMediaRepository
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.MediaWithTitle
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.TitledMediaList

interface FetchDiscoveryContentUseCase {
    suspend operator fun invoke(page: DiscoveryPage): Result<TitledMediaList, FetchDiscoveryContentError>
    class D (
        private val mediaRequirementsRepo: MediaRequirementsRepository,
        private val titledMediaRepo: TitledMediaRepository
    ) : FetchDiscoveryContentUseCase {

        private val pageCache: MutableMap<DiscoveryPage, TitledMediaList> = mutableMapOf()

        override suspend fun invoke(page: DiscoveryPage): Result<TitledMediaList, FetchDiscoveryContentError> {
            return runCatching {
                val cachedPage = pageCache[page]
               if (cachedPage != null) {
                   cachedPage
               } else {
                   val requirements: List<MediaRequirements> = mediaRequirementsRepo.getForPage(page)
                   val allMedia: List<MediaWithTitle> = requirements.map {
                       val titledMedia: MediaWithTitle = titledMediaRepo.withRequirement(it)
                       titledMedia
                   }
                   TitledMediaList.Def(allMedia).also { pageCache[page] = it }
               }
            }.mapError { FailureToFetchDiscContent }
        }
    }

    class Fake : FetchDiscoveryContentUseCase {
        var forceFailure = false
        override suspend fun invoke(page: DiscoveryPage): Result<TitledMediaList, FetchDiscoveryContentError> {
            return if (!forceFailure) {
                Ok(TitledMediaList.Def(listOf(
                    MediaWithTitle.Def("title #1"),
                    MediaWithTitle.Def("title #2"),
                    MediaWithTitle.Def("title #3"),
                    MediaWithTitle.Def("title #4"),
                    MediaWithTitle.Def("title #5"),
                )))
            } else {
                Err(FailureToFetchDiscContent)
            }
        }
    }

}

sealed interface FetchDiscoveryContentError
object FailureToFetchDiscContent : FetchDiscoveryContentError

