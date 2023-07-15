package com.sunrisekcdeveloper.medialion.newarch.components.shared.data.singleMedia

sealed class SingleMediaNetworkDto(
    open val id: String,
    open val title: String,
) {
    data class MovieNetworkDto(
        override val id: String,
        override val title: String,
        val releaseDate: String,
    ) : SingleMediaNetworkDto(id, title)

    data class TVShow(
        override val id: String,
        override val title: String,
        val firstAirDate: String,
    ) : SingleMediaNetworkDto(id, title)
}