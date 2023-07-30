package com.sunrisekcdeveloper.medialion.oldArch

import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaCategory
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.ID
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.SingleMediaItem
import com.sunrisekcdeveloper.medialion.oldArch.domain.MediaType
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Overview
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title

data class MediaItemUI constructor(
    val id: String,
    val title: String,
    val posterUrl: String = "",
    val bannerUrl: String = "",
    val categories: List<MediaCategory>,
    val overview: String,
    val popularity: Double,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseYear: String,
    val mediaType: MediaType,
) {
    fun toDomain(): SingleMediaItem {
        return when(mediaType) {
            MediaType.MOVIE -> SingleMediaItem.Movie(
                id = ID.Def(id),
                title = Title(title),
                backdropPath = this.bannerUrl,
                posterPath = this.posterUrl,
                categories = this.categories,
                popularity = this.popularity,
                voteAverage = this.voteAverage,
                voteCount = this.voteCount,
                adult = false,
                overview = Overview(this.overview),
                releaseDate = this.releaseYear
            )
            MediaType.TV -> SingleMediaItem.TVShow(
                id = ID.Def(id),
                title = Title(title),
                backdropPath = this.bannerUrl,
                posterPath = this.posterUrl,
                categories = this.categories,
                popularity = this.popularity,
                voteAverage = this.voteAverage,
                voteCount = this.voteCount,
                adult = false,
                overview = Overview(this.overview),
                firstAirDate = this.releaseYear,
            )
        }
    }

    companion object {
        fun from(domain: SingleMediaItem): MediaItemUI {
            return when (domain) {
                is SingleMediaItem.Movie -> MediaItemUI(
                    id = domain.id.uniqueIdentifier(),
                    title = domain.title.value,
                    posterUrl = domain.posterPath,
                    bannerUrl = domain.backdropPath,
                    categories = domain.categories,
                    overview = domain.overview.value,
                    popularity = domain.popularity,
                    voteAverage = domain.voteAverage,
                    voteCount = domain.voteCount,
                    releaseYear = domain.releaseDate,
                    mediaType = MediaType.MOVIE,
                )
                is SingleMediaItem.TVShow -> MediaItemUI(
                    id = domain.id.uniqueIdentifier(),
                    title = domain.title.value,
                    posterUrl = domain.posterPath,
                    bannerUrl = domain.backdropPath,
                    categories = domain.categories,
                    overview = domain.overview.value,
                    popularity = domain.popularity,
                    voteAverage = domain.voteAverage,
                    voteCount = domain.voteCount,
                    releaseYear = domain.firstAirDate,
                    mediaType = MediaType.TV,
                )
            }
        }
    }
}