package com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models

interface ID {
    fun uniqueIdentifier(): String
    class Def(uuidFactory: UUIDFactory) : ID {
        constructor() : this(UUIDFactoryImpl())

        private val id = uuidFactory.createUUID()

        override fun uniqueIdentifier(): String {
            return id
        }
    }
}