package com.sunrisekcdeveloper.medialion.features.mycollection

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.sunrisekcdeveloper.medialion.components.collections.domain.FetchAllCollectionsAsTitledMediaUseCase
import com.sunrisekcdeveloper.medialion.components.shared.domain.FetchAllCollectionsUseCaseNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.ID
import com.sunrisekcdeveloper.medialion.utils.debug
import com.sunrisekcdeveloper.medialion.utils.flow.CStateFlow
import com.sunrisekcdeveloper.medialion.utils.flow.cStateFlow
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface MLMyCollectionViewModelNew {
    val collectionState: CStateFlow<MyCollectionsUIState>
    fun submit(action: MyCollectionsAction)

    class Default(
        private val fetchMyCollectionsUseCase: FetchAllCollectionsAsTitledMediaUseCase,
        private val fetchAllCollectionsUseCase: FetchAllCollectionsUseCaseNew,
        coroutineScope: CoroutineScope? = null // // nullable due to iOS
    ) : MLMyCollectionViewModelNew {

        private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main.immediate)

        private val _screenState: StateFlow<MyCollectionsUIState> = fetchAllCollectionsUseCase()
            .map { result ->
                var state: MyCollectionsUIState = Loading
                result
                    .onSuccess { state = MyCollectionsContent(ID.Def(), it) }
                    .onFailure { state = FailedToFetchCollections }
                state
            }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), Loading)
        override val collectionState: CStateFlow<MyCollectionsUIState>
            get() = _screenState.cStateFlow()

        init { submit(FetchMyCollectionsMedia) }

        override fun submit(action: MyCollectionsAction) {
           // TODO remove we have no actions
        }
    }
}

