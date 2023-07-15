package com.sunrisekcdeveloper.medialion.components.discovery.domain.repo

import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.DiscoveryPage
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaCategory
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaRequirements
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaTypeNew

interface MediaRequirementsRepository {
    suspend fun getForPage(page: DiscoveryPage): List<MediaRequirements>

    class D(
        private val mediaCategoryRepository: MediaCategoryRepository
    ) : MediaRequirementsRepository {

        override suspend fun getForPage(page: DiscoveryPage): List<MediaRequirements> = runCatching {
            return when (page) {
                DiscoveryPage.All -> allPageRequirements()
                DiscoveryPage.Movies -> moviePageRequirements()
                DiscoveryPage.TVShows -> tvShowPageRequirements()
            }
        }.getOrElse { throw Exception("Failed to get media requirements for discovery page $page", it) }

        private suspend fun allPageRequirements(): List<MediaRequirements> {
            val categories: List<MediaCategory> = mediaCategoryRepository.getRandomOrAll(amount = 6)
            return categories.map {
                MediaRequirements(
                    withTitle = Title(value = it.name()),
                    withMediaType = MediaTypeNew.All,
                    withCategories = listOf(it),
                    withText = ""
                )
            }
        }

        private suspend fun moviePageRequirements(): List<MediaRequirements> {
            val categories: List<MediaCategory> = mediaCategoryRepository.getRandomOrAll(amount = 6, mediaType = MediaTypeNew.Movie)
            return categories.map {
                MediaRequirements(
                    withTitle = Title(value = it.name()),
                    withMediaType = MediaTypeNew.All,
                    withCategories = listOf(it),
                    withText = ""
                )
            }
        }

        private suspend fun tvShowPageRequirements(): List<MediaRequirements> {
            val categories: List<MediaCategory> = mediaCategoryRepository.getRandomOrAll(amount = 6, mediaType = MediaTypeNew.TVShow)
            return categories.map {
                MediaRequirements(
                    withTitle = Title(value = it.name()),
                    withMediaType = MediaTypeNew.All,
                    withCategories = listOf(it),
                    withText = ""
                )
            }
        }
    }

    class Fake : MediaRequirementsRepository {

        var forceFailure = false

        private val allCategories: List<MediaCategory> = listOf(
            MediaCategory.D(appliesToType = MediaTypeNew.TVShow),
            MediaCategory.D(appliesToType = MediaTypeNew.Movie),
            MediaCategory.D(appliesToType = MediaTypeNew.Movie),
            MediaCategory.D(appliesToType = MediaTypeNew.Movie),
            MediaCategory.D(appliesToType = MediaTypeNew.TVShow),
            MediaCategory.D(appliesToType = MediaTypeNew.Movie),
            MediaCategory.D(appliesToType = MediaTypeNew.Movie),
            MediaCategory.D(appliesToType = MediaTypeNew.Movie),
            MediaCategory.D(appliesToType = MediaTypeNew.Movie),
            MediaCategory.D(appliesToType = MediaTypeNew.Movie),
            MediaCategory.D(appliesToType = MediaTypeNew.Movie),
            MediaCategory.D(appliesToType = MediaTypeNew.Movie),
            MediaCategory.D(appliesToType = MediaTypeNew.Movie),
        )

        override suspend fun getForPage(page: DiscoveryPage): List<MediaRequirements> {
            if (forceFailure) throw Exception("Forced a failure")
            return when (page) {
                DiscoveryPage.All -> createRequirementsFor(
                    mediaType = MediaTypeNew.All,
                    withCategories = allCategories
                        .shuffled()
                        .take(6)
                )

                DiscoveryPage.Movies -> createRequirementsFor(
                    mediaType = MediaTypeNew.Movie,
                    withCategories = allCategories
                        .filter { category ->
                            category.appliesToType(MediaTypeNew.Movie)
                        }
                        .shuffled()
                        .take(6)
                )

                DiscoveryPage.TVShows -> createRequirementsFor(
                    mediaType = MediaTypeNew.TVShow,
                    withCategories = allCategories
                        .filter { mediaCategory -> mediaCategory.appliesToType(MediaTypeNew.TVShow) }
                        .shuffled()
                        .take(6)
                )
            }
        }

        private fun createRequirementsFor(mediaType: MediaTypeNew, withCategories: List<MediaCategory>): List<MediaRequirements> {
            return withCategories.map {
                MediaRequirements(
                    withMediaType = mediaType,
                    withCategories = listOf(it),
                    withText = "",
                    withTitle = Title("Title")
                )
            }
        }
    }
}