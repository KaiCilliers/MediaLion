package com.sunrisekcdeveloper.medialion.components.shared.domain.models

import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title

sealed interface SingleMediaItem {
    fun name(): Title
    fun identifier(): ID

    sealed class D(
        open val id: ID,
        open val title: Title
        ) : SingleMediaItem {
        override fun name(): Title {
            return title
        }

        override fun identifier(): ID {
            return id
        }
    }
    data class Movie(
        override val id: ID,
        override val title: Title
    ) : SingleMediaItem.D(id, title), SingleMediaItem {
        constructor(name: String) : this(
            id = ID.Def(),
            title = Title(name)
        )

        override fun equals(other: Any?): Boolean {
            return when {
                other == null || other !is SingleMediaItem.Movie -> false
                other.identifier().uniqueIdentifier() == id.uniqueIdentifier() -> true
                else -> false
            }
        }

        override fun hashCode(): Int {
            return id.uniqueIdentifier().hashCode()
        }
    }

    data class TVShow(
        override val id: ID,
        override val title: Title
    ) : SingleMediaItem.D(id, title), SingleMediaItem {
        constructor(name: String) : this(
            id = ID.Def(),
            title = Title(name)
        )

        override fun equals(other: Any?): Boolean {
            return when {
                other == null || other !is SingleMediaItem.TVShow -> false
                other.identifier().uniqueIdentifier() == id.uniqueIdentifier() -> true
                else -> false
            }
        }

        override fun hashCode(): Int {
            return id.uniqueIdentifier().hashCode()
        }
    }
}