package com.example.medialion.data.models.supporting

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// todo adjust visibility modifiers of models
@Serializable
internal data class CreatedBy(

    @SerialName("credit_id")
    val creditId: String?,

    @SerialName("gender")
    val gender: Int?,

    @SerialName("id")
    val id: Int?,

    @SerialName("name")
    val name: String?,

    @SerialName("profile_path")
    val profilePath: String?,
)