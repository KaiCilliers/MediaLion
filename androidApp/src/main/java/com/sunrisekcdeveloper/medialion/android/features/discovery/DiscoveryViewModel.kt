package com.sunrisekcdeveloper.medialion.android.features.discovery

import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.DiscoveryPage
import com.sunrisekcdeveloper.medialion.features.discovery.CategoriesUIState
import com.sunrisekcdeveloper.medialion.features.discovery.DiscoveryUIState
import com.sunrisekcdeveloper.medialion.features.discovery.FetchAllCategories
import com.sunrisekcdeveloper.medialion.features.discovery.FetchPageMediaContent
import com.sunrisekcdeveloper.medialion.features.discovery.MLCategoriesViewModel
import com.sunrisekcdeveloper.medialion.features.discovery.MLDiscoveryViewModelNew
import com.sunrisekcdeveloper.medialion.features.shared.MLMiniCollectionViewModel
import com.sunrisekcdeveloper.medialion.features.shared.MiniCollectionUIState
import kotlinx.coroutines.flow.StateFlow

class DiscoveryViewModel(
    private val sharedDiscoveryViewModel: MLDiscoveryViewModelNew,
    private val sharedCategoriesViewModel: MLCategoriesViewModel,
    private val sharedMiniCollectionViewModel: MLMiniCollectionViewModel,
) : MLDiscoveryViewModelNew by sharedDiscoveryViewModel,
    MLCategoriesViewModel by sharedCategoriesViewModel,
    MLMiniCollectionViewModel by sharedMiniCollectionViewModel {

    init {
        submit(FetchPageMediaContent(DiscoveryPage.All))
        submit(FetchAllCategories)
    }

    val discoveryState: StateFlow<DiscoveryUIState> = discState
    val categoriesState: StateFlow<CategoriesUIState> = catState
    val collectionDialogState: StateFlow<MiniCollectionUIState> = miniCollectionState
}