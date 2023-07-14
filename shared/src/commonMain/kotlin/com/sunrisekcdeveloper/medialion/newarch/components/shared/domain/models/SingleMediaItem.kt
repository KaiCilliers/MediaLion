package com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models

import com.sunrisekcdeveloper.medialion.domain.value.Title

interface SingleMediaItem {
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
    ) : SingleMediaItem.D(id, title) {
        constructor(name: String) : this(
            id = ID.Def(),
            title = Title(name)
        )
    }

    data class TVShow(
        override val id: ID,
        override val title: Title
    ) : SingleMediaItem.D(id, title) {
        constructor(name: String) : this(
            id = ID.Def(),
            title = Title(name)
        )
    }
}