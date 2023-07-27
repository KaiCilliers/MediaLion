package com.sunrisekcdeveloper.medialion.android.features.root

import android.os.Parcelable
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaCategory
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategorySelection(
    val result: (MediaCategory) -> Unit = {}
) : Parcelable