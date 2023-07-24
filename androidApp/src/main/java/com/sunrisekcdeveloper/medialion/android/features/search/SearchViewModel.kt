package com.sunrisekcdeveloper.medialion.android.features.search

import com.sunrisekcdeveloper.medialion.features.search.MLSearchViewModelNew
import com.sunrisekcdeveloper.medialion.features.search.SearchUIState
import com.sunrisekcdeveloper.medialion.features.shared.MLMiniCollectionViewModel
import com.sunrisekcdeveloper.medialion.features.shared.MiniCollectionUIState
import kotlinx.coroutines.flow.StateFlow

class SearchViewModel(
    sharedSearchViewModel: MLSearchViewModelNew,
    sharedMLMiniCollectionViewModel: MLMiniCollectionViewModel,
) : MLSearchViewModelNew by sharedSearchViewModel, MLMiniCollectionViewModel by sharedMLMiniCollectionViewModel {
    val searchScreenState: StateFlow<SearchUIState> = searchState
    val collectionDialogState: StateFlow<MiniCollectionUIState> = miniCollectionState
}

