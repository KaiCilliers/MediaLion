package com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.repo

import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models.MediaCategory
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models.MediaTypeNew

interface MediaCategoryRepository {
    suspend fun all(): List<MediaCategory>
    suspend fun getRandom(amount: Int, mediaType: MediaTypeNew = MediaTypeNew.All): List<MediaCategory>

    class Fake : MediaCategoryRepository {

        var forceFailure = false

        private val mediaCategories = listOf(
            MediaCategory.D("1"),
            MediaCategory.D("2", appliesToType = MediaTypeNew.Movie),
            MediaCategory.D("3"),
            MediaCategory.D("4", appliesToType = MediaTypeNew.TVShow),
            MediaCategory.D("5"),
            MediaCategory.D("6", appliesToType = MediaTypeNew.TVShow),
            MediaCategory.D("7"),
            MediaCategory.D("8"),
            MediaCategory.D("9"),
            MediaCategory.D("10"),
        )

        override suspend fun all(): List<MediaCategory> {
            if (forceFailure) throw Exception("Forced a Failure")
            return mediaCategories
        }

        override suspend fun getRandom(amount: Int, mediaType: MediaTypeNew): List<MediaCategory> {
            if (forceFailure) throw Exception("Forced a Failure")
            return mediaCategories
                .shuffled()
                .filter { mediaCategory -> mediaCategory.appliesToType(mediaType) }
                .take(amount)
        }
    }
}