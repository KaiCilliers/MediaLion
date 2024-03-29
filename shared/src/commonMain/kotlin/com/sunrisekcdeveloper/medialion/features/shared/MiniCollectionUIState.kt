package com.sunrisekcdeveloper.medialion.features.shared

import com.sunrisekcdeveloper.medialion.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.ID

sealed interface MiniCollectionUIState
data class Content(val stateId: ID, val collections: List<CollectionNew>) : MiniCollectionUIState
object FailedToLoadCollections : MiniCollectionUIState
object Loading : MiniCollectionUIState