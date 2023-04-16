package com.example.medialion.domain.components.search

import com.example.medialion.domain.models.MovieUiModel

sealed class SearchState(open val searchQuery: String) {
    data class Loading(
        override val searchQuery: String,
    ) : SearchState(searchQuery)
    data class Idle(
        override val searchQuery: String,
        val suggestedMedia: List<MovieUiModel>,
    ) : SearchState(searchQuery)
    data class Results(
        override val searchQuery: String,
        val searchResults: List<MovieUiModel>,
        val relatedTitles: List<List<MovieUiModel>>,
    ) : SearchState(searchQuery)
    data class Empty(
        override val searchQuery: String
    ) : SearchState(searchQuery)
}