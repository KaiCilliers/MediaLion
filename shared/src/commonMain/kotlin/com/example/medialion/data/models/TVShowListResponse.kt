package com.example.medialion.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TVShowListResponse (
    @SerialName("backdrop_path")
    val backdropPath: String?,

    @SerialName("genre_ids")
    val genreIds: List<Int>?,

    @SerialName("id")
    val id: Int?,

    @SerialName("original_language")
    val originalLanguage: String?,

    @SerialName("overview")
    val overview: String?,

    @SerialName("popularity")
    val popularity: Double?,

    @SerialName("poster_path")
    val posterPath: String?,

    @SerialName("name")
    val name: String?,

    @SerialName("vote_average")
    val voteAverage: Double?,

    @SerialName("vote_count")
    val voteCount: Int?,

    @SerialName("first_air_date")
    val firstAirDate: String?,

    @SerialName("origin_country")
    val originCountry: List<String>?,

    @SerialName("original_name")
    val originalName: String,
)