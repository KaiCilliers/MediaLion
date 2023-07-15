package com.sunrisekcdeveloper.medialion.components.shared.domain.models

import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title

interface MediaWithTitle {
    fun title(): Title
    fun media(): List<SingleMediaItem>
    class Def(
        private val title: Title,
        private val content: List<SingleMediaItem> = emptyList(),
    ) : MediaWithTitle {
        constructor(title: String): this(Title(title))

        override fun title(): Title {
            return title
        }

        override fun media(): List<SingleMediaItem> {
            return content
        }
    }

    data class LimitedMediaSizeWithTitle(
        private val origin: MediaWithTitle
    ) : MediaWithTitle by origin {
        init {
            require(origin.media().size == 32)
        }
    }
}

