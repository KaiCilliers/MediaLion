package com.example.medialion.data.models.supporting

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCountry(

    @SerialName("iso_3166_1")
    val isoCode: String?,

    @SerialName("name")
    val name: String?,
)