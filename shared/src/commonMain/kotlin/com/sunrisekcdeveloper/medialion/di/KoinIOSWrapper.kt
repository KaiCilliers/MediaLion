package com.sunrisekcdeveloper.medialion.di

import com.sunrisekcdeveloper.medialion.components.collections.domain.AddUpdateCollectionUseCase
import com.sunrisekcdeveloper.medialion.components.collections.domain.DeleteCollectionUseCaseNew
import com.sunrisekcdeveloper.medialion.components.collections.domain.FetchAllCollectionsAsTitledMediaUseCase
import com.sunrisekcdeveloper.medialion.components.collections.domain.InsertDefaultCollectionsUseCase
import com.sunrisekcdeveloper.medialion.components.discovery.domain.FetchDiscoveryContentUseCase
import com.sunrisekcdeveloper.medialion.components.discovery.domain.FetchMediaWithCategoryUseCase
import com.sunrisekcdeveloper.medialion.components.discovery.domain.FetchSuggestedMediaUseCase
import com.sunrisekcdeveloper.medialion.components.discovery.domain.SearchForMediaUseCase
import com.sunrisekcdeveloper.medialion.components.shared.domain.FetchAllMediaCategoriesUseCase
import com.sunrisekcdeveloper.medialion.features.discovery.MLCategoriesViewModel
import com.sunrisekcdeveloper.medialion.features.discovery.MLDiscoveryViewModelNew
import com.sunrisekcdeveloper.medialion.features.mycollection.MLMyCollectionViewModelNew
import com.sunrisekcdeveloper.medialion.features.search.MLSearchViewModelNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.FetchAllCollectionsUseCaseNew
import com.sunrisekcdeveloper.medialion.features.discovery.InsertDefaultCollections
import com.sunrisekcdeveloper.medialion.features.shared.MLMiniCollectionViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(sharedModule)
    }
}

class WrappedMLMiniCollectionViewModel : KoinComponent {
    fun instance() = MLMiniCollectionViewModel.D(
        get<FetchAllCollectionsUseCaseNew>(),
        get<DeleteCollectionUseCaseNew>(),
        get<AddUpdateCollectionUseCase>(),
        coroutineScope = null,
    )
}

class WrappedMLSearchViewModelNew : KoinComponent {
    fun instance() = MLSearchViewModelNew.Default(
        get<SearchForMediaUseCase>(),
        get<FetchSuggestedMediaUseCase>(),
        coroutineScope = null,
    )
}

class WrappedMLCollectionViewModelNew : KoinComponent {
    fun instance() = MLMyCollectionViewModelNew.Default(
        get<FetchAllCollectionsAsTitledMediaUseCase>(),
        get<FetchAllCollectionsUseCaseNew>(),
        coroutineScope = null,
    )
}

class WrappedMLDiscoveryViewModelNew : KoinComponent {
    fun instance() = MLDiscoveryViewModelNew.D(
        get<FetchDiscoveryContentUseCase>(),
        get<FetchMediaWithCategoryUseCase>(),
        get<InsertDefaultCollectionsUseCase>(),
        coroutineScope = null,
    )
}

class WrappedMLCategoriesViewModel : KoinComponent {
    fun instance() = MLCategoriesViewModel.D(
        get<FetchAllMediaCategoriesUseCase>(),
        coroutineScope = null,
    )
}