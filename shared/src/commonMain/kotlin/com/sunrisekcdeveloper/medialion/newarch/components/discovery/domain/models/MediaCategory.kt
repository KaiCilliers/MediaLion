package com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models

interface MediaCategory {
    fun name(): String
    fun appliesToType(type: MediaTypeNew): Boolean
    fun typeAppliedTo(): MediaTypeNew

    data class D(
        private val name: String = "",
        private val appliesToType: MediaTypeNew = MediaTypeNew.All
    ) : MediaCategory {
        override fun name(): String {
            return name
        }

        override fun appliesToType(type: MediaTypeNew): Boolean {
            return type == appliesToType || appliesToType == MediaTypeNew.All
        }

        override fun typeAppliedTo(): MediaTypeNew {
            return appliesToType
        }
    }
}