package com.example.medialion.search

import com.example.medialion.models.MovieUiModel

sealed class SearchState {
    object Loading : SearchState()
    data class Idle(
        val suggestedMedia: List<MovieUiModel>
    ) : SearchState()
    data class Results(
        val searchResults: List<MovieUiModel>,
        val relatedTitles: List<List<MovieUiModel>>,
    ) : SearchState()
    object Empty : SearchState()
}