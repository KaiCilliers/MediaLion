package com.sunrisekcdeveloper.medialion.android.ui.collections

import com.sunrisekcdeveloper.medialion.features.mycollection.MLMyCollectionViewModelNew
import com.sunrisekcdeveloper.medialion.features.mycollection.MyCollectionsUIState
import com.sunrisekcdeveloper.medialion.features.shared.MLMiniCollectionViewModel
import com.sunrisekcdeveloper.medialion.features.shared.MiniCollectionUIState
import kotlinx.coroutines.flow.StateFlow

class CollectionViewModel(
    private val sharedCollectionViewModel: MLMyCollectionViewModelNew,
    private val sharedMiniCollectionViewModel: MLMiniCollectionViewModel,
) : MLMyCollectionViewModelNew by sharedCollectionViewModel,
    MLMiniCollectionViewModel by sharedMiniCollectionViewModel {

    val collectionsState: StateFlow<MyCollectionsUIState> = collectionState
    val collectionDialogState: StateFlow<MiniCollectionUIState> = miniCollectionState

}