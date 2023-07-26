package com.sunrisekcdeveloper.medialion.features.mycollection

import com.sunrisekcdeveloper.medialion.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.ID
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.TitledMediaList

sealed interface MyCollectionsUIState
object FailedToFetchCollections : MyCollectionsUIState
object Loading : MyCollectionsUIState
data class MyCollectionsContent(
    val id: ID, // used to always process new content states
    val collections: List<CollectionNew>
) : MyCollectionsUIState