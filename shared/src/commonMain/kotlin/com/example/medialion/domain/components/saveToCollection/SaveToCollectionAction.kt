package com.example.medialion.domain.components.saveToCollection

sealed class SaveToCollectionAction {
    data class AddNewCollectionWithMedia(
        val collectionName: String,
        val mediaId: Int,
    )
}