package com.sunrisekcdeveloper.medialion.newarch.search

import com.sunrisekcdeveloper.medialion.newarch.search.models.SearchQuery

sealed class SearchScreenAction {
    data class SubmitSearchQuery(val query: SearchQuery) : SearchScreenAction()
}