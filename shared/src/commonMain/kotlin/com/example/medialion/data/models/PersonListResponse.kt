package com.example.medialion.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonListResponse(
    @SerialName("adult")
    val adult: Boolean?,

    @SerialName("gender")
    val gender: Int?,

    @SerialName("known_for")
    val knownFor: List<MultiTypeResponse>?,

    @SerialName("name")
    val name: String?,

    @SerialName("popularity")
    val popularity: Int?,

    @SerialName("profile_path")
    val profilePath: String?,
)