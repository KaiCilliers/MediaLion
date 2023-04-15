package com.example.medialion.domain.components.saveToCollection

sealed class SaveToCollectionState {
    data class CollectionsList(
        val collections: List<String>
    ) : SaveToCollectionState()
}