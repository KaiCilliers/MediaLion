package com.sunrisekcdeveloper.medialion.features.shared

import com.sunrisekcdeveloper.medialion.components.shared.domain.models.CollectionNew

sealed interface MiniCollectionUIState
data class Content(val collections: List<CollectionNew>) : MiniCollectionUIState
object FailedToLoadCollections : MiniCollectionUIState
object Loading : MiniCollectionUIState