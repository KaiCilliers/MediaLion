package com.example.medialion.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagedKeywordResults(
    @SerialName("page")
    val page: Int,

    @SerialName("results")
    val results: List<KeywordResponse>,

    @SerialName("total_pages")
    val totalPages: Int,

    @SerialName("total_results")
    val totalResults: Int,
)