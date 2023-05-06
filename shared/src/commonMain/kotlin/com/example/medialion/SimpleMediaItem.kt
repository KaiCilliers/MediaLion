package com.example.medialion

import com.example.medialion.domain.MediaType

data class SimpleMediaItem(
    val id: String,
    val title: String,
    val description: String = "",
    val year: String = "",
    val posterUrl: String,
    val mediaType: MediaType,
)