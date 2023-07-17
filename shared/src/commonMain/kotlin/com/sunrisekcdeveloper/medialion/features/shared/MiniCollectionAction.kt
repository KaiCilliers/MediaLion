package com.sunrisekcdeveloper.medialion.features.shared

import com.sunrisekcdeveloper.medialion.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.features.mycollection.MyCollectionsAction
import com.sunrisekcdeveloper.medialion.features.mycollection.MyCollectionsUIState
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title
import com.sunrisekcdeveloper.medialion.oldArch.flow.CStateFlow

sealed interface MiniCollectionAction
data class UpdateCollection(val collection: CollectionNew) : MiniCollectionAction
data class CreateCollection(val title: Title) : MiniCollectionAction
data class DeleteCollection(val collection: CollectionNew) : MiniCollectionAction
 // EXAMPLE STRUCTURE
interface ExampleMyCollectionViewModel {
    val myCollectionState: CStateFlow<MyCollectionsUIState>
    fun submit(action: MyCollectionsAction)
}

interface ExampleNewCollectionsViewModel {
    val collectionsState: CStateFlow<List<CollectionNew>>
    fun submit(action: MiniCollectionAction)
}

class FooViewModel(
    private val s1: ExampleMyCollectionViewModel,
    private val s2: ExampleNewCollectionsViewModel
) : ExampleMyCollectionViewModel by s1, ExampleNewCollectionsViewModel by s2