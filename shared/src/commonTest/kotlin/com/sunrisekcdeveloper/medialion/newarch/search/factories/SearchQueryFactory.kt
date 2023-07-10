package com.sunrisekcdeveloper.medialion.newarch.search.factories

import com.sunrisekcdeveloper.medialion.newarch.search.models.Clock
import com.sunrisekcdeveloper.medialion.newarch.search.models.SearchQuery

class SearchQueryFactory(
    private var query: String = "default",
    private var clock: Clock = Clock.Default()
) {
    fun asExecutable(): SearchQueryFactory = apply {
        query = "default "
    }

    fun asNotExecutable(): SearchQueryFactory = apply {
        query = "The"
    }

    fun produce(): SearchQuery {
        return SearchQuery.Default(query, clock)
    }
}