package com.sunrisekcdeveloper.medialion.domain.entities

import com.sunrisekcdeveloper.medialion.MediaItemUI
import com.sunrisekcdeveloper.medialion.domain.value.Title

data class CollectionWithMedia(
    val name: Title,
    val contents: List<MediaItem>,
)

data class CollectionWithMediaUI(
    val name: Title,
    val contents: List<MediaItemUI>,
)