package com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models

import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.Clock
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.SearchQuery
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.time.Duration.Companion.milliseconds

class SearchQueryTest {

    private lateinit var searchQuery: SearchQuery
    private lateinit var clock: Clock.Fake

    @BeforeTest
    fun setup() {
        clock = Clock.Fake()
        searchQuery = SearchQuery.Default("1234", clock)
    }

    @Test
    fun `when search query is less than 4 characters it is not executable`() {
        searchQuery = SearchQuery.Default("asd")
        assertThat(searchQuery.canPerformQuery()).isFalse()
    }

    @Test
    fun `when search query exceeds 3 characters it can be executed`() {
        assertThat(searchQuery.canPerformQuery()).isTrue()
    }

    @Test
    fun `when the search query has been updated less than 400 millis ago it can not be executed`() {
        clock.advanceTimeBy(380.milliseconds)
        searchQuery.update("12345")

        assertThat(searchQuery.canPerformQuery()).isFalse()
    }

    @Test
    fun `when the search query has been updated more than 400 millis since the previous update it can be executed`() {
        clock.advanceTimeBy(400.milliseconds)
        searchQuery.update("12345")

        assertThat(searchQuery.canPerformQuery()).isTrue()
    }
}