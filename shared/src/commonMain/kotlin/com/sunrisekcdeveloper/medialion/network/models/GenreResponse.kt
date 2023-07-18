package com.sunrisekcdeveloper.medialion.network.models

import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    val id: Int,
    val name: String
)