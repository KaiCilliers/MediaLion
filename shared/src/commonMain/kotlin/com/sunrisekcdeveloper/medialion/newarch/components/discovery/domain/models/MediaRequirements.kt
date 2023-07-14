package com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models

import com.sunrisekcdeveloper.medialion.domain.value.Title
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.ID

data class MediaRequirements(
    val withTitle: Title,
    val mediaType: List<MediaTypeNew>,
    val categories: List<MediaCategory>,
    val withText: String,
    val mediaIdsToExclude: List<ID> = emptyList(),
    val amountOfItems: Int = 32,
)