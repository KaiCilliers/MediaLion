package com.sunrisekcdeveloper.medialion.components.discovery.domain.models

import com.sunrisekcdeveloper.medialion.components.shared.domain.models.ID

interface MediaCategory {
    fun name(): String
    fun appliesToType(type: MediaTypeNew): Boolean
    fun typeAppliedTo(): MediaTypeNew
    fun identifier(): ID

    data class D(
        val id: ID = ID.Def(),
        private val name: String = "",
        private val appliesToType: MediaTypeNew = MediaTypeNew.All
    ) : MediaCategory {
        constructor(id: String) : this(id = ID.Def(id))
        constructor(id: String, name: String = "", appliesToType: MediaTypeNew = MediaTypeNew.All) : this(
            id = ID.Def(id),
            name = name,
            appliesToType = appliesToType
        )

        override fun identifier(): ID {
            return id
        }

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