package com.sunrisekcdeveloper.medialion.features.search

import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.SearchResults

sealed class SearchUIState {
    object TopSuggestions : SearchUIState()
    object Loading : SearchUIState()
    data class Results(val results: SearchResults) : SearchUIState()
    object NoResults : SearchUIState()
}

