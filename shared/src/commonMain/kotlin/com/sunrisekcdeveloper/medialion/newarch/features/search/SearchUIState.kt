package com.sunrisekcdeveloper.medialion.newarch.features.search

import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models.SearchResults

sealed class SearchUIState {
    object TopSuggestions : SearchUIState()
    object Loading : SearchUIState()
    data class Results(val results: SearchResults) : SearchUIState()
    object NoResults : SearchUIState()
}

