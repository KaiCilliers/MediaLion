package com.sunrisekcdeveloper.medialion.domain.search

import com.sunrisekcdeveloper.medialion.MediaItemUI

sealed class SearchState(open val searchQuery: String) {
    data class Loading(
        override val searchQuery: String,
    ) : SearchState(searchQuery)
    data class Idle(
        override val searchQuery: String,
        val suggestedMedia: List<MediaItemUI>,
    ) : SearchState(searchQuery)
    data class Results(
        override val searchQuery: String,
        val searchResults: List<MediaItemUI>,
        val relatedTitles: List<List<MediaItemUI>>,
    ) : SearchState(searchQuery)
    data class Empty(
        override val searchQuery: String
    ) : SearchState(searchQuery)
}