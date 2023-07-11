package com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models

import com.sunrisekcdeveloper.medialion.domain.value.Title

interface CollectionNew {
    fun asMediaWithTitle(): MediaWithTitle
    fun title(): Title
    fun rename(newTitle: Title): CollectionNew
    fun identifier(): ID

    data class Def(
        private val id: ID,
        private var title: Title
    ) : CollectionNew {
        constructor(name: String) : this(ID.Def(), Title(name))

        override fun asMediaWithTitle(): MediaWithTitle {
            return MediaWithTitle.Def(title)
        }

        override fun title(): Title {
            return title
        }

        override fun identifier(): ID {
            return id
        }

        override fun rename(newTitle: Title): CollectionNew {
            return copy(
                id = this.id,
                title = newTitle
            )
        }

        override fun equals(other: Any?): Boolean {
            return when {
                other == null || other !is Def -> false
                other.identifier().uniqueIdentifier() == id.uniqueIdentifier() -> true
                else -> false
            }
        }

        override fun hashCode(): Int {
            return id.uniqueIdentifier().hashCode()
        }
    }
}