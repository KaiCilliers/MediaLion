package com.sunrisekcdeveloper.medialion.domain.value

import kotlin.jvm.JvmInline

@JvmInline
value class Rating(val value: Double) {
    init {
        check(value >= 0) { "Rating must not be negative" }
    }
}