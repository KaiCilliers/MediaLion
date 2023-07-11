package com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models

import com.sunrisekcdeveloper.medialion.domain.value.Title

interface SingleMediaItem {
    class Def(
        private val title: Title
    ) : SingleMediaItem {
        constructor(name: String) : this(Title(name))
    }
}