package com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models

import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title

interface CollectionNew {
    fun asMediaWithTitle(): MediaWithTitle
    fun title(): Title
    fun rename(newTitle: Title): CollectionNew
    fun identifier(): ID
    fun add(item: SingleMediaItem)
    fun media(): List<SingleMediaItem>
    fun remove(item: SingleMediaItem)

    data class Def(
        private val id: ID,
        private var title: Title,
        private var media: List<SingleMediaItem>,
    ) : CollectionNew {
        constructor(name: String) : this(ID.Def(), Title(name), emptyList())
        constructor(name: String, media: List<SingleMediaItem>) : this(ID.Def(), Title(name), media)

        override fun remove(item: SingleMediaItem) {
            media.toMutableList().run {
                remove(item)
                media = this
            }
        }

        override fun add(item: SingleMediaItem) {
            media.toMutableList().run {
                add(item)
                media = this
            }
        }

        override fun media(): List<SingleMediaItem> {
            return media
        }

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