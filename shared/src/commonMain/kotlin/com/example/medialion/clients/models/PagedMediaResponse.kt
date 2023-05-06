package com.example.medialion.clients.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagedMediaResponse(
    @SerialName("page")
    val page: Int,

    @SerialName("results")
    val results: List<MediaResponse>,

    @SerialName("total_pages")
    val totalPages: Int,

    @SerialName("total_results")
    val totalResults: Int,
)