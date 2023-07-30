package com.sunrisekcdeveloper.medialion.oldArch

import com.sunrisekcdeveloper.medialion.oldArch.domain.MediaType

data class SimpleMediaItem(
    val id: String,
    val title: String,
    val description: String = "",
    val year: String = "",
    val posterUrl: String,
    val mediaType: MediaType,
) {
    companion object {
        fun from(item: MediaItemUI): SimpleMediaItem {
            return SimpleMediaItem(
                id = item.id,
                title = item.title,
                description = item.overview,
                year = item.releaseYear,
                posterUrl = item.posterUrl,
                mediaType = item.mediaType

            )
        }
    }
}