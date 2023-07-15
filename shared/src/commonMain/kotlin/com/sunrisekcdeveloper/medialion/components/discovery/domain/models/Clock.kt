package com.sunrisekcdeveloper.medialion.components.discovery.domain.models

import kotlinx.datetime.Instant
import kotlin.time.Duration
import kotlinx.datetime.Clock as KotlinClock

interface Clock {
    fun now(): Instant

    class Default : Clock {
        override fun now(): Instant {
            return KotlinClock.System.now()
        }
    }

    class Fake : Clock {

        private var currentTime = KotlinClock.System.now()

        override fun now(): Instant {
            return currentTime
        }

        fun advanceTimeBy(millis: Duration) {
            currentTime = currentTime.plus(millis)
        }

    }
}