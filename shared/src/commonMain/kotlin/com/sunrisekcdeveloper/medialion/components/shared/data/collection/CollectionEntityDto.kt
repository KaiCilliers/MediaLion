package com.sunrisekcdeveloper.medialion.components.shared.data.collection

import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.ID
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.SingleMediaItem

data class CollectionEntityDto(
    val id: ID,
    var title: Title,
    var media: List<SingleMediaItem>,
) {
    constructor(id: String) : this(
        id = ID.Def(id),
        title = Title(id),
        media = listOf()
    )

    override fun equals(other: Any?): Boolean {
        return when {
            other == null || other !is CollectionEntityDto -> false
            other.id.uniqueIdentifier() == id.uniqueIdentifier() -> true
            else -> false
        }
    }

    override fun hashCode(): Int {
        return id.uniqueIdentifier().hashCode()
    }
}