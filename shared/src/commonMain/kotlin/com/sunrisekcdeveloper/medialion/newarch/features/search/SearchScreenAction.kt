package com.sunrisekcdeveloper.medialion.newarch.features.search

import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models.SearchQuery

sealed class SearchScreenAction {
    data class SubmitSearchQuery(val query: SearchQuery) : SearchScreenAction()
}