package com.sunrisekcdeveloper.medialion.domain.entities

import com.sunrisekcdeveloper.medialion.domain.value.Title

data class Collection(
    val name: Title,
    val contents: List<MediaItem>,
)