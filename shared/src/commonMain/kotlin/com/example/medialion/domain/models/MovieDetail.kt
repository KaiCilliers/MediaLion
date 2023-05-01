package com.example.medialion.domain.models

import com.example.medialion.data.models.supporting.Genre

data class  MovieDetail(
    val adult: Boolean,
    val backdropPath: String,
    val budget: Int,
    val genres: List<Genre>,
    val id: Int,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String?,
    val revenue: Long,
    val runtime: Int,
    val tagline: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
)
