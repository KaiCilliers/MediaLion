package com.sunrisekcdeveloper.medialion.oldArch.domain.value

import kotlin.jvm.JvmInline

@JvmInline
value class ID(val value: Int) {
    init {
        check(value >= 0) { "ID value must not be negative" }
    }
}