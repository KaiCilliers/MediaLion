package com.sunrisekcdeveloper.medialion.clients.models

import kotlinx.serialization.Serializable

@Serializable
data class GenreWrapper(
    val genres: List<GenreResponse>,
)