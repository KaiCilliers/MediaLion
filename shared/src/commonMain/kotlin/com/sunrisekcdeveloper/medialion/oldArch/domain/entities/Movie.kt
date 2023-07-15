package com.sunrisekcdeveloper.medialion.oldArch.domain.entities

import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Date
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.ID
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.ImageURL
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Overview
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Rating
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title

data class Movie (
    val adult: Boolean,
    val backdropPath: ImageURL,
    val genreIds: List<ID>,
    override val id: ID,
    val overview: Overview,
    val popularity: Rating,
    val posterPath: ImageURL,
    val releaseDate: Date,
    val title: Title,
    val voteAverage: Rating,
    val voteCount: Int,
): MediaItem(id) {
    init {
        check(!adult) { "Adult movies are not allowed" }
    }
}
