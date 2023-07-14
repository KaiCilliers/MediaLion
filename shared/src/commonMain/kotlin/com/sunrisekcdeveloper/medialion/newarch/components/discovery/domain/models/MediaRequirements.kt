package com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models

import com.sunrisekcdeveloper.medialion.domain.value.Title
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.ID

data class MediaRequirements(
    val withTitle: Title,
    val withMediaTypes: List<MediaTypeNew> = listOf(MediaTypeNew.All),
    val withCategories: List<MediaCategory> = emptyList(),
    val withText: String = "",
    val withoutMedia: List<ID> = emptyList(),
    val amountOfMedia: Int = 32,
) {
    constructor(withTitle: Title, singleMediaType: MediaTypeNew) : this(
        withTitle = withTitle,
        withMediaTypes = listOf(singleMediaType)
    )

    constructor(withTitle: Title, singleCategory: MediaCategory) : this(
        withTitle = withTitle,
        withCategories = listOf(singleCategory)
    )

}