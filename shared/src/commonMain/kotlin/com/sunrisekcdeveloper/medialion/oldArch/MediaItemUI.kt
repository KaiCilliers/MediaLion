package com.sunrisekcdeveloper.medialion.oldArch

import com.sunrisekcdeveloper.medialion.oldArch.domain.MediaType

data class MediaItemUI(
    val id: Int,
    val title: String,
    val isFavorited: Boolean,
    val posterUrl: String = "",
    val bannerUrl: String = "",
    val genreIds: List<Int>,
    val overview: String,
    val popularity: Double,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseYear: String,
    val mediaType: MediaType,
)