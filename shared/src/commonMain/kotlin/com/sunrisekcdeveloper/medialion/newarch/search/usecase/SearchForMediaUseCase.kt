package com.sunrisekcdeveloper.medialion.newarch.search.usecase

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.sunrisekcdeveloper.medialion.newarch.search.models.SearchQuery
import com.sunrisekcdeveloper.medialion.newarch.search.models.SearchResults

interface SearchForMediaUseCase {
    suspend operator fun invoke(query: SearchQuery): Result<SearchResults, SearchForMediaError>

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

interface SearchForMediaError
object NoMediaFound : SearchForMediaError