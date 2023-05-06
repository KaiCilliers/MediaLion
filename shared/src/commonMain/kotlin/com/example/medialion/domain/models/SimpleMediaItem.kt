package com.example.medialion.domain.models

data class SimpleMediaItem(
    val id: String,
    val title: String,
    val description: String = "",
    val year: String = "",
    val posterUrl: String,
)