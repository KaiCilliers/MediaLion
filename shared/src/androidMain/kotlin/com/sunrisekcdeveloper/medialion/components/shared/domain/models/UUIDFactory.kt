package com.sunrisekcdeveloper.medialion.components.shared.domain.models

import java.util.UUID

actual class UUIDFactoryImpl : UUIDFactory {
    actual override fun createUUID(): String {
        // randomUUID returns a Platform type [UUID!] that can cause NullPointerExceptions
        return UUID.randomUUID().toString()
    }
}