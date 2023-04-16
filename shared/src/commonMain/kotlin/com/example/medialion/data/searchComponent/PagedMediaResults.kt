package com.example.medialion.data.searchComponent

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagedMediaResults(
    val page: Int?,

    val results: List<MediaResponse>?,

    @SerialName("total_pages")
    val totalPages: Int?,

    @SerialName("total_results")
    val totalResults: Int?
)