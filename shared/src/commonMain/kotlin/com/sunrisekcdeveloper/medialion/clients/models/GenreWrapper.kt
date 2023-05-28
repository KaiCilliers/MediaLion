package com.sunrisekcdeveloper.medialion.clients.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreWrapper(
    @SerialName("genres")
    val genres: List<GenreResponse>,
)