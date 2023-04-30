package com.example.medialion.domain.components.search.wip.domain

import kotlinx.serialization.SerialName

data class Movie (
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val id: Int,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String?,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
)