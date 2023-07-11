package com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models

import com.sunrisekcdeveloper.medialion.domain.value.Title

interface MediaWithTitle {
    fun title(): Title
    class Def(
        private val title: Title,
        private val content: List<SingleMediaItem> = emptyList(),
    ) : MediaWithTitle {
        constructor(title: String): this(Title(title))

        override fun title(): Title {
            return title
        }
    }
}

interface SingleMediaItem {
    class Def() : SingleMediaItem
}