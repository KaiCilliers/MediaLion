package com.sunrisekcdeveloper.medialion.components.shared.domain.models

interface ID {
    fun uniqueIdentifier(): String
    class Def(private val value: String) : ID {
        constructor() : this(UUIDFactoryImpl().createUUID())
        constructor(uuidFactory: UUIDFactory) : this(uuidFactory.createUUID())

        override fun uniqueIdentifier(): String {
            return value
        }

        override fun toString(): String {
            return uniqueIdentifier()
        }
    }
}