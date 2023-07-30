package com.sunrisekcdeveloper.medialion.features.search

import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.SearchQuery
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.SearchResults
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.SingleMediaItem

sealed class SearchUIState(
    open val searchQuery: SearchQuery
) {
    data class TopSuggestions(
        override val searchQuery: SearchQuery = SearchQuery.Default(""),
        val media: List<MediaWithFavorites>
    ) : SearchUIState(searchQuery)

    data class Loading(
        override val searchQuery: SearchQuery = SearchQuery.Default(""),
    ) : SearchUIState(searchQuery)

    data class Results(
        override val searchQuery: SearchQuery = SearchQuery.Default(""),
        val results: SearchResults
    ) : SearchUIState(searchQuery)

    data class NoResults(
        override val searchQuery: SearchQuery = SearchQuery.Default(""),
    ) : SearchUIState(searchQuery)
}

data class MediaWithFavorites(
    val mediaItem: SingleMediaItem,
    val favorited: Boolean,
)