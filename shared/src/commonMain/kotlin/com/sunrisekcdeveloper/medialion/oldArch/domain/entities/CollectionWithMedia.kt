package com.sunrisekcdeveloper.medialion.oldArch.domain.entities

import com.sunrisekcdeveloper.medialion.oldArch.MediaItemUI
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title

data class CollectionWithMedia(
    val name: Title,
    val contents: List<MediaItem>,
)

data class CollectionWithMediaUI(
    val name: Title,
    val contents: List<MediaItemUI>,
)