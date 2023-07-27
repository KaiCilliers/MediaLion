package com.sunrisekcdeveloper.medialion.features.search

import com.sunrisekcdeveloper.medialion.features.shared.MLMiniCollectionViewModel
import com.sunrisekcdeveloper.medialion.features.shared.MiniCollectionUIState
import kotlinx.coroutines.flow.StateFlow

class SearchViewModel(
    sharedSearchViewModel: MLSearchViewModelNew,
    sharedMLMiniCollectionViewModel: MLMiniCollectionViewModel, // todo remove - root screen handles this
) : MLSearchViewModelNew by sharedSearchViewModel, MLMiniCollectionViewModel by sharedMLMiniCollectionViewModel {
    val searchScreenState: StateFlow<SearchUIState> = searchState
}

