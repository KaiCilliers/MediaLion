package com.example.medialion.domain.entities

import com.example.medialion.domain.value.Date
import com.example.medialion.domain.value.ID
import com.example.medialion.domain.value.ImageURL
import com.example.medialion.domain.value.Overview
import com.example.medialion.domain.value.Rating
import com.example.medialion.domain.value.Title

data class TVShow (
    val adult: Boolean,
    val backdropPath: ImageURL,
    val genreIds: List<ID>,
    override val id: ID,
    val overview: Overview,
    val popularity: Rating,
    val posterPath: ImageURL,
    val name: Title,
    val voteAverage: Rating,
    val voteCount: Int,
    val firstAirDate: Date,
): MediaItem(id) {
    init {
        check(!adult) { "Adult tv shows are not allowed" }
    }
}

