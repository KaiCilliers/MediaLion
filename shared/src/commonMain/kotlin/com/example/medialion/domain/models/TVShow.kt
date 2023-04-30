package com.example.medialion.domain.models

data class TVShow (
    val backdropPath: String,
    val genreIds: List<Int>,
    val id: Int,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val name: String,
    val voteAverage: Double,
    val voteCount: Int,
    val firstAirDate: String,
)