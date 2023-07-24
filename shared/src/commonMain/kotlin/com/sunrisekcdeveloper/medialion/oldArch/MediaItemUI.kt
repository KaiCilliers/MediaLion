package com.sunrisekcdeveloper.medialion.oldArch

import com.sunrisekcdeveloper.medialion.components.shared.domain.models.ID
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.SingleMediaItem
import com.sunrisekcdeveloper.medialion.oldArch.domain.MediaType
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title

data class MediaItemUI constructor(
    val id: String,
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
) {
    fun toDomain(): SingleMediaItem {
        return when(mediaType) {
            MediaType.MOVIE -> SingleMediaItem.Movie(
                id = ID.Def(id),
                title = Title(title)
            )
            MediaType.TV -> SingleMediaItem.TVShow(
                id = ID.Def(id),
                title = Title(title)
            )
        }
    }

    companion object {
        fun from(domain: SingleMediaItem): MediaItemUI {
            return when (domain) {
                is SingleMediaItem.Movie -> MediaItemUI(
                    id = domain.id.uniqueIdentifier(),
                    title = domain.title.value,
                    isFavorited = false,
                    posterUrl = "",
                    bannerUrl = "",
                    genreIds = listOf(),
                    overview = "",
                    popularity = 0.0,
                    voteAverage = 0.0,
                    voteCount = 0,
                    releaseYear = "",
                    mediaType = MediaType.MOVIE,
                )
                is SingleMediaItem.TVShow -> MediaItemUI(
                    id = domain.id.uniqueIdentifier(),
                    title = domain.title.value,
                    isFavorited = false,
                    posterUrl = "",
                    bannerUrl = "",
                    genreIds = listOf(),
                    overview = "",
                    popularity = 0.0,
                    voteAverage = 0.0,
                    voteCount = 0,
                    releaseYear = "",
                    mediaType = MediaType.TV,
                )
            }
        }
    }
}