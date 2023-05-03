package com.example.medialion.domain.models

data class MediaItem(
    val id: Int,
    val title: String,
    val posterPath: String,
    val genreIds: List<Int>,
    val overview: String,
    val popularity: Double,
    val backdropPath: String,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseYear: String,
    val mediaType: MediaType,
)

enum class MediaType {
    MOVIE, TV
}