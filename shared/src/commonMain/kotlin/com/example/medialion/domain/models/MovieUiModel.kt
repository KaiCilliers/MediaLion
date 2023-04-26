package com.example.medialion.domain.models

data class MovieUiModel(
    val id: Int,
    val title: String,
    val isFavorited: Boolean,
    val description: String = "",
    val year: String = "",
    val posterUrl: String = "",
    val bannerUrl: String = "",
)