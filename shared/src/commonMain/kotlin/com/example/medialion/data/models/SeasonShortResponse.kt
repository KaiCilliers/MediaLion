package com.example.medialion.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeasonShortResponse(

    @SerialName("air")
    val airDate: String?,

    @SerialName("episode")
    val episodeCount: Int?,

    @SerialName("id")
    val id: Int?,

    @SerialName("name")
    val name: String?,

    @SerialName("overview")
    val overview: String?,

    @SerialName("poster")
    val posterPath: String?,

    @SerialName("season")
    val seasonNumber: Int?,
)