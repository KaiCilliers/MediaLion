package com.sunrisekcdeveloper.medialion.android.ui.search

import FetchDiscoveryContent
import com.sunrisekcdeveloper.medialion.oldArch.di.MapperNames
import com.sunrisekcdeveloper.medialion.oldArch.di.MapperNames.mediaDomainToUI
import com.sunrisekcdeveloper.medialion.oldArch.domain.collection.MLCollectionViewModel
import com.sunrisekcdeveloper.medialion.oldArch.domain.discovery.DiscoveryAction
import com.sunrisekcdeveloper.medialion.oldArch.domain.discovery.DiscoveryState
import com.sunrisekcdeveloper.medialion.oldArch.domain.discovery.MLDiscoveryViewModel
import com.sunrisekcdeveloper.medialion.oldArch.domain.entities.CollectionWithMedia
import com.sunrisekcdeveloper.medialion.oldArch.domain.search.CollectionComponent
import com.sunrisekcdeveloper.medialion.oldArch.domain.search.MLSearchViewModel
import com.sunrisekcdeveloper.medialion.oldArch.domain.search.SearchAction
import com.sunrisekcdeveloper.medialion.oldArch.domain.search.SearchComponent
import com.sunrisekcdeveloper.medialion.oldArch.domain.search.SearchState
import com.sunrisekcdeveloper.medialion.oldArch.mappers.ListMapper
import com.zhuinden.simplestack.Backstack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class SearchViewModel(
    backstack: Backstack
) : KoinComponent {

    private val searchComponent by inject<SearchComponent>()
    private val collectionComponent by inject<CollectionComponent>()

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val sharedViewModel: MLSearchViewModel by lazy {
        MLSearchViewModel(
            searchComponent = searchComponent,
            collectionComponent = collectionComponent,
            mediaItemMapper = get(named(mediaDomainToUI)),
            movieListMapper = ListMapper.Impl(get(named(MapperNames.movieDomainToUI))),
            tvListMapper = ListMapper.Impl(get(named(MapperNames.tvDomainToUI))),
            coroutineScope = viewModelScope,
        )
    }

    val state: StateFlow<SearchState> = sharedViewModel.state
    val collectionState: StateFlow<List<CollectionWithMedia>> = sharedViewModel.allCollectionsState

    fun submitAction(action: SearchAction) {
        sharedViewModel.submitAction(action)
    }
}

class DiscoveryViewModel(
    backstack: Backstack
) : KoinComponent {

    private val fetchDiscoveryContent by inject<FetchDiscoveryContent>()
    private val collectionComponent by inject<CollectionComponent>()
    private val searchComponent by inject<SearchComponent>()

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val sharedViewModel: MLDiscoveryViewModel by lazy {
       MLDiscoveryViewModel(
           fetchDiscoveryContent = fetchDiscoveryContent,
           collectionComponent = collectionComponent,
           searchComponent = searchComponent,
           coroutineScope = viewModelScope
       )
    }
    val genres: MLCollectionViewModel by lazy {
        MLCollectionViewModel(
            collectionComponent = collectionComponent,
            searchComponent = searchComponent,
            mediaListMapper = ListMapper.Impl(get(named(mediaDomainToUI))),
            coroutineScope = viewModelScope,
        )
    }

    init {
        submitAction(DiscoveryAction.FetchContent(null))
    }

    val state: StateFlow<DiscoveryState> = sharedViewModel.state

    fun submitAction(action: DiscoveryAction) {
        sharedViewModel.submitAction(action)
    }
}