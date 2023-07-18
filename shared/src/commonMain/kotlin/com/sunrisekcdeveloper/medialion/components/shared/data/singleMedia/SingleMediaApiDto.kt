package com.sunrisekcdeveloper.medialion.components.shared.data.singleMedia

sealed class SingleMediaApiDto(
    open val id: String,
    open val title: String,
) {
    data class Movie(
        override val id: String,
        override val title: String,
        val releaseDate: String,
    ) : SingleMediaApiDto(id, title)

    data class TVShow(
        override val id: String,
        override val title: String,
        val firstAirDate: String,
    ) : SingleMediaApiDto(id, title)
}