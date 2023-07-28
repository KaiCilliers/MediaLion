package com.sunrisekcdeveloper.medialion.components.shared.data.mediaCategory

import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaTypeNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.ID

data class MediaCategoryEntityDto(
    val id: ID,
    val name: String,
    val appliesToMedia: MediaTypeNew
) {
    constructor(id: String) : this(id = ID.Def(id), name = id, appliesToMedia = MediaTypeNew.All)
}