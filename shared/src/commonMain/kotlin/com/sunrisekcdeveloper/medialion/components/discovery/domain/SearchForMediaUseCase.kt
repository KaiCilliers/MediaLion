package com.sunrisekcdeveloper.medialion.components.discovery.domain

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaRequirementsFactory
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.SearchQuery
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.SearchResults
import com.sunrisekcdeveloper.medialion.components.discovery.domain.repo.TitledMediaRepository

interface SearchForMediaUseCase {
    suspend operator fun invoke(query: SearchQuery): Result<SearchResults, SearchForMediaError>

    class Def(
        private val titledMediaRepository: TitledMediaRepository,
        private val mediaRequirementsFactory: MediaRequirementsFactory,
    ) : SearchForMediaUseCase {
        override suspend fun invoke(query: SearchQuery): Result<SearchResults, SearchForMediaError> {
            return if (query.toString().length > 2) {
                runCatching {
                    val requirements = mediaRequirementsFactory.fromSearchQuery(query)
                    val titledMedia = titledMediaRepository.withRequirement(requirements)
                    if (titledMedia.media().isEmpty()) {
                        throw NoSuchElementException()
                    } else {
                        SearchResults.Default(titledMedia)
                    }
                }.mapError {
                    when (it) {
                        is NoSuchElementException -> NoMediaFound
                      else -> Failure(Exception(it))
                    }
                }
            } else {
                Err(SearchQueryNotReady)
            }
        }
    }

    class Fake : SearchForMediaUseCase {
        var returnEmptyResults = false

        override suspend fun invoke(query: SearchQuery): Result<SearchResults, SearchForMediaError> {
            return if (returnEmptyResults) {
                Err(NoMediaFound)
            } else {
                Ok(SearchResults.Default())
            }
        }
    }
}

sealed interface SearchForMediaError
data class Failure(val throwable: Exception?) : SearchForMediaError
object NoMediaFound : SearchForMediaError
object SearchQueryNotReady : SearchForMediaError

fun <T> T.toOk(): Ok<T> {
    return Ok(this)
}