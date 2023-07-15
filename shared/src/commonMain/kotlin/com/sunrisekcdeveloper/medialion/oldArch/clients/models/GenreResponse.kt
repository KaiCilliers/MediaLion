package com.sunrisekcdeveloper.medialion.oldArch.clients.models

import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    val id: Int,
    val name: String
)