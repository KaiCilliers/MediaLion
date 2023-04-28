package com.example.medialion.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeasonResponse(

    @SerialName("_id")
    val responseId: String?,

    @SerialName("air_date")
    val airDate: String?,

    @SerialName("episodes")
    val episodes: List<EpisodeResponse>?,

    @SerialName("id")
    val seasonId: Int?,

    @SerialName("name")
    val name: String?,

    @SerialName("overview")
    val overview: String?,

    @SerialName("poster_path")
    val posterPath: String?,

    @SerialName("season_number")
    val seasonNumber: Int?,
)