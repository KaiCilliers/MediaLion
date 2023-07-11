package com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models

import platform.Foundation.NSUUID

actual class UUIDFactoryImpl : UUIDFactory {
    actual override fun createUUID(): String {
        return NSUUID().UUIDString
    }
}