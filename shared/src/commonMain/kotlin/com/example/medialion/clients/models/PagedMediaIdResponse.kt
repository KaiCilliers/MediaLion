package com.example.medialion.clients.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagedMediaIdResponse(
    @SerialName("page")
    val page: Int,

    @SerialName("results")
    val results: List<MediaIdResponse>,

    @SerialName("total_pages")
    val totalPages: Int,

    @SerialName("total_results")
    val totalResults: Int,
)