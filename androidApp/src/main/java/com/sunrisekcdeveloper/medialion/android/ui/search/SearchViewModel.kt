package com.sunrisekcdeveloper.medialion.android.ui.search

import com.sunrisekcdeveloper.medialion.di.MapperNames
import com.sunrisekcdeveloper.medialion.domain.entities.Collection
import com.sunrisekcdeveloper.medialion.domain.search.CollectionComponent
import com.sunrisekcdeveloper.medialion.domain.search.MLSearchViewModel
import com.sunrisekcdeveloper.medialion.domain.search.SearchAction
import com.sunrisekcdeveloper.medialion.domain.search.SearchComponent
import com.sunrisekcdeveloper.medialion.domain.search.SearchState
import com.sunrisekcdeveloper.medialion.mappers.ListMapper
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
            mediaItemMapper = get(named(MapperNames.mediaDomainToUI)),
            movieListMapper = ListMapper.Impl(get(named(MapperNames.movieDomainToUI))),
            tvListMapper = ListMapper.Impl(get(named(MapperNames.tvDomainToUI))),
            coroutineScope = viewModelScope,
        )
    }

    val state: StateFlow<SearchState> = sharedViewModel.state
    val collectionState: StateFlow<List<Collection>> = sharedViewModel.allCollectionsState

    fun submitAction(action: SearchAction) {
        sharedViewModel.submitAction(action)
    }
}