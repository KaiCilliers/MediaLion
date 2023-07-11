package com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models

import com.sunrisekcdeveloper.medialion.domain.value.Title

interface SingleMediaItem {
    fun name(): Title
    fun identifier(): ID
    class Def(
        private val id: ID,
        private val title: Title
    ) : SingleMediaItem {
        constructor(name: String) : this(
            id = ID.Def(),
            title = Title(name)
        )

        override fun name(): Title {
            return title
        }

        override fun identifier(): ID {
            return id
        }

        override fun equals(other: Any?): Boolean {
            return when {
                other == null || other !is SingleMediaItem.Def -> false
                other.identifier().uniqueIdentifier() == id.uniqueIdentifier() -> true
                else -> false
            }
        }

        override fun hashCode(): Int {
            return id.uniqueIdentifier().hashCode()
        }
    }
}