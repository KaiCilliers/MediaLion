package com.sunrisekcdeveloper.medialion.newarch.components.shared.data.mediaCategory

interface CategoryRemoteClient {
    suspend fun allCategories(): List<MediaCategoryApiDto>
}