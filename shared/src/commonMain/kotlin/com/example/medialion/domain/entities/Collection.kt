package com.example.medialion.domain.entities

import com.example.medialion.domain.value.Title

data class Collection(
    val name: Title,
    val contents: List<MediaItem>,
)