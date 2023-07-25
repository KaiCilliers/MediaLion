package com.sunrisekcdeveloper.medialion.features.shared

import com.github.michaelbull.result.map
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.sunrisekcdeveloper.medialion.components.collections.domain.AddUpdateCollectionUseCase
import com.sunrisekcdeveloper.medialion.components.collections.domain.DeleteCollectionUseCaseNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.FetchAllCollectionsUseCaseNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.ID
import com.sunrisekcdeveloper.medialion.utils.flow.CStateFlow
import com.sunrisekcdeveloper.medialion.utils.flow.cStateFlow
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

interface MLMiniCollectionViewModel {
    val miniCollectionState: CStateFlow<MiniCollectionUIState>
    fun submit(action: MiniCollectionAction)

    class D(
        fetchAllCollectionsUseCase: FetchAllCollectionsUseCaseNew,
        private val deleteCollectionUseCase: DeleteCollectionUseCaseNew,
        private val addUpdateCollectionUseCase: AddUpdateCollectionUseCase,
        coroutineScope: CoroutineScope? = null // // nullable due to iOS
    ) : MLMiniCollectionViewModel {

        private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main.immediate)

        private val _minCollectionState: StateFlow<MiniCollectionUIState> = fetchAllCollectionsUseCase()
            .map { result ->
                var state: MiniCollectionUIState = Loading
                result
                    .onSuccess { state = Content(ID.Def(), it) }
                    .onFailure { state = FailedToLoadCollections }
                state
            }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), Loading)

        override val miniCollectionState: CStateFlow<MiniCollectionUIState>
            get() = _minCollectionState.cStateFlow()

        override fun submit(action: MiniCollectionAction) {
            Napier.d { "Processing $action" }
            when(action) {
                is CreateCollection -> viewModelScope.launch {
                    // do not create objects like this - use factory
                    addUpdateCollectionUseCase(CollectionNew.Def(action.title.value))
                }
                is DeleteCollection -> viewModelScope.launch { deleteCollectionUseCase(action.collection) }
                is UpdateCollection -> viewModelScope.launch { addUpdateCollectionUseCase(action.collection) }
                is InsertCollection -> viewModelScope.launch { addUpdateCollectionUseCase(action.collection) }
            }
        }
    }
}

