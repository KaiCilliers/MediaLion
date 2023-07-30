package com.sunrisekcdeveloper.medialion.features.root

import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaCategory

data class CategorySelection(val result: (MediaCategory) -> Unit = {})