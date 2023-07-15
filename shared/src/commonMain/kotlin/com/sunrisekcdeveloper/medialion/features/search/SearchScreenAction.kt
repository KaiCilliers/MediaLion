package com.sunrisekcdeveloper.medialion.features.search

import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.SearchQuery

sealed class SearchScreenAction {
    data class SubmitSearchQuery(val query: SearchQuery) : SearchScreenAction()
}