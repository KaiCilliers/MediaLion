package com.sunrisekcdeveloper.medialion

import com.sunrisekcdeveloper.medialion.domain.MediaType

data class SimpleMediaItem(
    val id: String,
    val title: String,
    val description: String = "",
    val year: String = "",
    val posterUrl: String,
    val mediaType: MediaType,
)