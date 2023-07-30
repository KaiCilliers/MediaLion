package com.sunrisekcdeveloper.medialion.components.shared.domain.models

import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title

interface TitledMediaList {

    fun collectionNames(): List<Title>
    fun clearMedia()
    fun all(): List<MediaWithTitle>

  data  class Def(private var content: List<MediaWithTitle>) : TitledMediaList {
        constructor(singleItem: MediaWithTitle) : this(listOf(singleItem))

        override fun collectionNames(): List<Title> {
            return content.map { it.title() }
        }

        override fun clearMedia() {
            content = emptyList()
        }

      override fun all(): List<MediaWithTitle> {
          return content
      }
    }
}