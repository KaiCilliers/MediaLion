package com.sunrisekcdeveloper.medialion.oldArch.clients.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaIdResponse(

    @SerialName("id")
    val id: Int?,

    @SerialName("adult")
    val adult: Boolean?,
)