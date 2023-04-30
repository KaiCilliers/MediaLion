package com.example.medialion.domain.models

data class OldMovie(
    val backdropPath: String?,
    val genreIds: List<Int>,
    val id: Int,
    val language: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val favorited: Boolean = false,
)

