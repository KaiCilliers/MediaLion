package com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models

interface UUIDFactory {
    fun createUUID(): String
}

expect class UUIDFactoryImpl() : UUIDFactory {
    override fun createUUID(): String
}