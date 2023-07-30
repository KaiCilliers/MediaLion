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
import com.sunrisekcdeveloper.medialion.features.search.MediaWithFavorites
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

interface FetchSuggestedMediaUseCase {
    @Deprecated("implementation has been replaced by asFlow - either update this implementation of delete")
    suspend operator fun invoke(): Result<MediaWithTitle, FetchSuggestedMediaError>
    fun asFlow(): Flow<Result<List<MediaWithFavorites>, FetchSuggestedMediaError>>

    class Def(
        private val mediaRequirementsFactory: MediaRequirementsFactory,
        private val titledMediaRepository: TitledMediaRepository,
        private val collectionRepository: CollectionRepositoryNew,
    ) : FetchSuggestedMediaUseCase {
        @Deprecated("implementation has been replaced by asFlow - either update this implementation of delete")
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

        override fun asFlow(): Flow<Result<List<MediaWithFavorites>, FetchSuggestedMediaError>> = flow {
            val requirements = mediaRequirementsFactory.suggestedMediaRequirements()
            val allCollections = collectionRepository.all()
            val excludedIds = runCatching {
                allCollections
                    .flatMap { singleCollection -> singleCollection.media() }
                    .map { it.identifier() }
            }.getOr(emptyList())
            val titledMedia = titledMediaRepository.withRequirement(requirements.copy(withoutMedia = excludedIds))

            emitAll(
                collectionRepository
                    .observe()
                    .map { collections -> collections.find { it.title().value == "Favorites" } }
                    .map { collection ->
                        Ok(
                            titledMedia.media().map { item ->
                                MediaWithFavorites(
                                    mediaItem = item,
                                    favorited = collection?.media()?.contains(item) ?: false
                                )
                            }
                        )
                    }
            )
        }
    }

    class Fake : FetchSuggestedMediaUseCase {
        @Deprecated("implementation has been replaced by asFlow - either update this implementation of delete")
        override suspend fun invoke(): Result<MediaWithTitle, FetchSuggestedMediaError> {
            return Ok(MediaWithTitle.Def("a"))
        }

        override fun asFlow(): Flow<Result<List<MediaWithFavorites>, FetchSuggestedMediaError>> {
            TODO("Not yet implemented")
        }
    }
}

sealed interface FetchSuggestedMediaError
object FailedToFetchFeatureMedia : FetchSuggestedMediaError