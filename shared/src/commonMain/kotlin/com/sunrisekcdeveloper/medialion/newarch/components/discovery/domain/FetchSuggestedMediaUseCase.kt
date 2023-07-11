package com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.getOrThrow
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import com.sunrisekcdeveloper.medialion.domain.value.Title
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.MediaWithTitle
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.repos.CollectionRepositoryNew
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList

interface FetchSuggestedMediaUseCase {
    suspend operator fun invoke(): Result<MediaWithTitle, FetchSuggestedMediaError>

    class Def(
        singleMediaItemRepository: SingleMediaItemRepository,
        private val collectionRepository: CollectionRepositoryNew
    ) : FetchSuggestedMediaUseCase {

        private val singleMediaItemSmartRepo = SingleMediaItemRepository.Smart(singleMediaItemRepository)

        override suspend fun invoke(): Result<MediaWithTitle, FetchSuggestedMediaError> {
            val collectionMedia = collectionRepository
                .all()
                .map {
                    it.flatMap { it.media() }
                }
            val titledMedia = runCatching {
                singleMediaItemSmartRepo
                    .suggestedMediaFlow()
                    .getOrThrow { throw Exception(it.toString()) }
                    .filterNot { upstreamItem ->
                        val (success, _) = collectionMedia
                        success?.contains(upstreamItem) ?: false
                    }
                    .take(32)
                    .toList()
                    .run {
                        MediaWithTitle.Def(
                            title = Title("Suggested Media"),
                            content = this
                        )
                    }
            }
                .mapError { FailedToFetchFeatureMedia }
            return titledMedia
        }
    }
}

sealed interface FetchSuggestedMediaError
object FailedToFetchFeatureMedia : FetchSuggestedMediaError