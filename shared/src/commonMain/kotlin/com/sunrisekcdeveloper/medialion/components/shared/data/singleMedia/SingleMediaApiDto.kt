package com.sunrisekcdeveloper.medialion.components.shared.data.singleMedia

import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaCategory
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.ID
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Overview
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title

sealed class SingleMediaApiDto(
    open val id: String,
    open val title: String,
    open val backdropPath: String,
    open val posterPath: String,
    open val popularity: Double,
    open val voteAverage: Double,
    open val adult: Boolean,
    open val overview: String,
    open val genreIds: List<Int>,
    open val voteCount: Int,
) {
    data class Movie(
        override val id: String,
        override val title: String,
        override val backdropPath: String,
        override val posterPath: String,
        override val popularity: Double,
        override val voteAverage: Double,
        override val adult: Boolean,
        override val overview: String,
        override val genreIds: List<Int>,
        override val voteCount: Int,
        val releaseDate: String,
    ) : SingleMediaApiDto(id, title, backdropPath, posterPath, popularity, voteAverage, adult, overview, genreIds, voteCount) {
        constructor(id: String, title: String, releaseDate: String) : this(
            id = id,
            title = title,
            backdropPath = "",
            posterPath = "",
            popularity = 0.0,
            voteAverage = 0.0,
            adult = false,
            overview = "",
            genreIds = listOf(),
            voteCount = 0,
            releaseDate = releaseDate
        )
    }

    data class TVShow(
        override val id: String,
        override val title: String,
        override val backdropPath: String,
        override val posterPath: String,
        override val popularity: Double,
        override val voteAverage: Double,
        override val adult: Boolean,
        override val overview: String,
        override val genreIds: List<Int>,
        override val voteCount: Int,
        val firstAirDate: String,
    ) : SingleMediaApiDto(id, title, backdropPath, posterPath, popularity, voteAverage, adult, overview, genreIds, voteCount) {
        constructor(id: String, title: String, firstAirDate: String) : this(
            id = id,
            title = title,
            backdropPath = "",
            posterPath = "",
            popularity = 0.0,
            voteAverage = 0.0,
            adult = false,
            overview = "",
            genreIds = listOf(),
            voteCount = 0,
            firstAirDate = firstAirDate
        )
    }
}