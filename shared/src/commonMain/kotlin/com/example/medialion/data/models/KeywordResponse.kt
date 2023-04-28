package com.example.medialion.data.models

import kotlinx.serialization.Serializable

@Serializable
data class KeywordResponse(
    val id: Int,
    val name: String
)