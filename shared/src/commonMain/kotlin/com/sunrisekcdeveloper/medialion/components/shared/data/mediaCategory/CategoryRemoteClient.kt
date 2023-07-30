package com.sunrisekcdeveloper.medialion.components.shared.data.mediaCategory

interface CategoryRemoteClient {
    suspend fun allCategories(): List<MediaCategoryApiDto>
}