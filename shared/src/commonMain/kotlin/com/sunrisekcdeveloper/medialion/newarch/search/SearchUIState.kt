package com.sunrisekcdeveloper.medialion.newarch.search

import com.sunrisekcdeveloper.medialion.newarch.search.models.SearchResults

sealed class SearchUIState {
    object TopSuggestions : SearchUIState()
    object Loading : SearchUIState()
    data class Results(val results: SearchResults) : SearchUIState()
    object NoResults : SearchUIState()
}

