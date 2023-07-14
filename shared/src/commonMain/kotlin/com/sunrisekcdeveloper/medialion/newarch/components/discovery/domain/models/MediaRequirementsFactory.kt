package com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models

import com.sunrisekcdeveloper.medialion.domain.value.Title

interface MediaRequirementsFactory {
    fun fromSearchQuery(query: SearchQuery): MediaRequirements
    fun fromCategory(category: MediaCategory): MediaRequirements
    fun suggestedMediaRequirements(): MediaRequirements

    class D : MediaRequirementsFactory {
        override fun fromSearchQuery(query: SearchQuery): MediaRequirements {
            return MediaRequirements(
                withTitle = Title(value = "Search Results"),
                withMediaTypes = listOf(MediaTypeNew.All),
                withCategories = listOf(),
                withText = query.toString()
            )
        }

        override fun fromCategory(category: MediaCategory): MediaRequirements {
            return MediaRequirements(
                withTitle = Title(value = ""),
                withMediaTypes = category.appliesToTypes(),
                withCategories = listOf(),
                withText = ""
            )
        }

        override fun suggestedMediaRequirements(): MediaRequirements {
            return MediaRequirements(
                withTitle = Title(value = "Suggested Media"),
                withMediaTypes = listOf(MediaTypeNew.All),
                withCategories = listOf(),
                withText = ""
            )
        }
    }

    class Fake : MediaRequirementsFactory {

        var forceFailure = false

        override fun fromSearchQuery(query: SearchQuery): MediaRequirements {
            if (forceFailure) throw Exception("Forced Failure")
            return MediaRequirements(
                withTitle = Title(value = "Search Results"),
                withMediaTypes = listOf(MediaTypeNew.All),
                withCategories = listOf(),
                withText = query.toString()
            )
        }

        override fun fromCategory(category: MediaCategory): MediaRequirements {
            if (forceFailure) throw Exception("Forced Failure")
            return MediaRequirements(
                withTitle = Title(value = ""),
                withMediaTypes = category.appliesToTypes(),
                withCategories = listOf(),
                withText = ""
            )
        }

        override fun suggestedMediaRequirements(): MediaRequirements {
            if (forceFailure) throw Exception("Forced Failure")
            return MediaRequirements(
                withTitle = Title(value = "Suggested Media"),
                withMediaTypes = listOf(MediaTypeNew.All),
                withCategories = listOf(),
                withText = ""
            )
        }
    }
}