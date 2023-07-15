package com.sunrisekcdeveloper.medialion.newarch.components.shared.data.mediaCategory

import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models.MediaTypeNew

data class MediaCategoryApiDto(
    val id: String,
    val name: String,
    val appliesTo: MediaTypeNew = MediaTypeNew.All
) {
    override fun equals(other: Any?): Boolean {
        return when {
            other == null || other !is MediaCategoryApiDto -> false
            other.id == id -> true
            else -> false
        }
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}