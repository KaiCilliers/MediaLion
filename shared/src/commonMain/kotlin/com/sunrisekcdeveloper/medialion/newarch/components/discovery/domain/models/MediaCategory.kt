package com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models

interface MediaCategory {
    fun name(): String
    fun appliesToType(type: MediaTypeNew): Boolean
    fun appliesToTypes(): List<MediaTypeNew>

    data class D(
        private val name: String = "",
        private val appliesToTypes: List<MediaTypeNew>
    ) : MediaCategory {
        constructor(name: String, appliesToAllTypes: Boolean = true) : this(
            name, if (appliesToAllTypes) listOf(MediaTypeNew.All) else listOf()
        )
        constructor(name: String, appliesToType: MediaTypeNew): this(
            name, listOf(appliesToType)
        )

        override fun name(): String {
            return name
        }

        override fun appliesToType(type: MediaTypeNew): Boolean {
            return appliesToTypes.contains(type) || appliesToTypes.contains(MediaTypeNew.All)
        }

        override fun appliesToTypes(): List<MediaTypeNew> {
            return appliesToTypes
        }
    }
}