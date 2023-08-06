package com.sunrisekcdeveloper.medialion.features.discovery

import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.DiscoveryPage
import com.sunrisekcdeveloper.medialion.features.shared.MLMiniCollectionViewModel
import kotlinx.coroutines.flow.StateFlow

class DiscoveryViewModel(
    private val sharedDiscoveryViewModel: MLDiscoveryViewModelNew,
    private val sharedCategoriesViewModel: MLCategoriesViewModel,
    private val sharedMiniCollectionViewModel: MLMiniCollectionViewModel,
) : MLDiscoveryViewModelNew by sharedDiscoveryViewModel,
    MLCategoriesViewModel by sharedCategoriesViewModel,
    MLMiniCollectionViewModel by sharedMiniCollectionViewModel {

    init {
        submit(InsertDefaultCollections)
        submit(FetchPageMediaContent(DiscoveryPage.All))
        submit(FetchAllCategories)
    }

    val discoveryState: StateFlow<DiscoveryUIState> = discState
    val categoriesState: StateFlow<CategoriesUIState> = catState
}