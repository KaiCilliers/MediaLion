package com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models

import kotlinx.datetime.Instant
import kotlin.time.Duration.Companion.milliseconds

interface SearchQuery {
    fun update(query: String)
    fun canPerformQuery(): Boolean

    class Default(
        private var value: String,
        private val clock: Clock
    ) : SearchQuery {

        constructor(value: String) : this (
            value = value,
            clock = Clock.Default()
        )

        companion object {
            private const val MIN_CHARACTERS_FOR_EXECUTABLE_QUERY = 3
        }

        private var lastUpdateTimeStamp: Instant = clock.now()
        private var millisSinceLastUpdate = Int.MAX_VALUE.milliseconds

        override fun update(query: String) {
            trackUpdates()
            value = query
        }

        override fun canPerformQuery(): Boolean {
            return value.trim().length > MIN_CHARACTERS_FOR_EXECUTABLE_QUERY
                    && millisSinceLastUpdate >= 400.milliseconds
        }

        private fun trackUpdates() {
            millisSinceLastUpdate = clock.now() - lastUpdateTimeStamp
            lastUpdateTimeStamp = clock.now()
        }

        override fun toString(): String {
            return value
        }
    }
}