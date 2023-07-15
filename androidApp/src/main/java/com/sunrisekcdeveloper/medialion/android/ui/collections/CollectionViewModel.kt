package com.sunrisekcdeveloper.medialion.android.ui.collections

import com.sunrisekcdeveloper.medialion.oldArch.di.MapperNames
import com.sunrisekcdeveloper.medialion.oldArch.domain.collection.CollectionAction
import com.sunrisekcdeveloper.medialion.oldArch.domain.collection.CollectionState
import com.sunrisekcdeveloper.medialion.oldArch.domain.collection.MLCollectionViewModel
import com.sunrisekcdeveloper.medialion.oldArch.domain.search.CollectionComponent
import com.sunrisekcdeveloper.medialion.oldArch.domain.search.SearchComponent
import com.sunrisekcdeveloper.medialion.oldArch.mappers.ListMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class CollectionViewModel(
) : KoinComponent {

    private val searchComponent by inject<SearchComponent>()
    private val collectionComponent by inject<CollectionComponent>()

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val sharedViewModel: MLCollectionViewModel by lazy {
        MLCollectionViewModel(
            collectionComponent = collectionComponent,
            searchComponent = searchComponent,
            mediaListMapper = ListMapper.Impl(get(named(MapperNames.mediaDomainToUI))),
            coroutineScope = viewModelScope
        )
    }

    val state: StateFlow<CollectionState> = sharedViewModel.state

    fun submitAction(action: CollectionAction) {
        sharedViewModel.submitAction(action)
    }
}