package com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models

import com.sunrisekcdeveloper.medialion.domain.value.Title

interface TitledMediaList {

    fun collectionNames(): List<Title>
    fun clearMedia()

  class Def(private var content: List<MediaWithTitle>) : TitledMediaList {
        constructor(singleItem: MediaWithTitle) : this(listOf(singleItem))

        override fun collectionNames(): List<Title> {
            return content.map { it.title() }
        }

        override fun clearMedia() {
            content = emptyList()
        }
    }
}