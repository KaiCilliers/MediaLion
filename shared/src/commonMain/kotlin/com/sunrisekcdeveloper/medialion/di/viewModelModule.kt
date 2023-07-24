package com.sunrisekcdeveloper.medialion.di

import com.sunrisekcdeveloper.medialion.components.collections.domain.AddUpdateCollectionUseCase
import com.sunrisekcdeveloper.medialion.components.collections.domain.DeleteCollectionUseCaseNew
import com.sunrisekcdeveloper.medialion.components.collections.domain.FetchAllCollectionsAsTitledMediaUseCase
import com.sunrisekcdeveloper.medialion.components.discovery.domain.FetchDiscoveryContentUseCase
import com.sunrisekcdeveloper.medialion.components.discovery.domain.FetchMediaWithCategoryUseCase
import com.sunrisekcdeveloper.medialion.components.discovery.domain.FetchSuggestedMediaUseCase
import com.sunrisekcdeveloper.medialion.components.discovery.domain.SearchForMediaUseCase
import com.sunrisekcdeveloper.medialion.components.shared.domain.FetchAllCollectionsUseCaseNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.FetchAllMediaCategoriesUseCase
import com.sunrisekcdeveloper.medialion.features.discovery.MLCategoriesViewModel
import com.sunrisekcdeveloper.medialion.features.discovery.MLDiscoveryViewModelNew
import com.sunrisekcdeveloper.medialion.features.mycollection.MLMyCollectionViewModelNew
import com.sunrisekcdeveloper.medialion.features.search.MLSearchViewModelNew
import com.sunrisekcdeveloper.medialion.features.shared.MLMiniCollectionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val viewModelModule = module {
    factory<MLSearchViewModelNew> {
        MLSearchViewModelNew.Default(
            get<SearchForMediaUseCase>(),
            get<FetchSuggestedMediaUseCase>(),
            coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate),
        )
    }

    factory<MLMiniCollectionViewModel> {
        MLMiniCollectionViewModel.D(
            get<FetchAllCollectionsUseCaseNew>(),
            get<DeleteCollectionUseCaseNew>(),
            get<AddUpdateCollectionUseCase>(),
            coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate),
        )
    }

    factory<MLMyCollectionViewModelNew> {
        MLMyCollectionViewModelNew.Default(
            get<FetchAllCollectionsAsTitledMediaUseCase>(),
            coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate),
        )
    }

    factory<MLDiscoveryViewModelNew> {
        MLDiscoveryViewModelNew.D(
            get<FetchDiscoveryContentUseCase>(),
            get<FetchMediaWithCategoryUseCase>(),
            coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate),
        )
    }

    factory<MLCategoriesViewModel> {
        MLCategoriesViewModel.D(
            get<FetchAllMediaCategoriesUseCase>(),
            coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate),
        )
    }
}