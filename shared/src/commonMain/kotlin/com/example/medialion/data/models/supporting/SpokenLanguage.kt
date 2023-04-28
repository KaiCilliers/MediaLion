package com.example.medialion.data.models.supporting

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpokenLanguage(

    @SerialName("english_name")
    val englishName: String?,

    @SerialName("iso_639_1")
    val isoCode: String?,

    @SerialName("name")
    val name: String?,
)