package com.example.medialion.clients.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaResponse(
    @SerialName("adult")
    val adult: Boolean?,

    @SerialName("backdrop_path")
    val backdropPath: String?,

    @SerialName("genre_ids")
    val genreIds: List<Int>?,

    @SerialName("id")
    val id: Int?,

    @SerialName("original_title")
    val originalTitle: String?,

    @SerialName("overview")
    val overview: String?,

    @SerialName("popularity")
    val popularity: Double?,

    @SerialName("poster_path")
    val posterPath: String?,

    @SerialName("release_date")
    val releaseDate: String?,

    @SerialName("title")
    val title: String?,

    @SerialName("vote_average")
    val voteAverage: Double?,

    @SerialName("vote_count")
    val voteCount: Int?,

    @SerialName("first_air_date")
    val firstAirDate: String?,

    @SerialName("media_type")
    val mediaType: String?,

    @SerialName("name")
    val name: String?,

    @SerialName("original_name")
    val originalName: String?,
)