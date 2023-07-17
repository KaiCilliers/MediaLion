package com.sunrisekcdeveloper.medialion.features.mycollection

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.sunrisekcdeveloper.medialion.components.collections.domain.FetchAllCollectionsAsTitledMediaUseCase
import com.sunrisekcdeveloper.medialion.oldArch.flow.CStateFlow
import com.sunrisekcdeveloper.medialion.oldArch.flow.cStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface MLMyCollectionViewModelNew {
    val state: CStateFlow<MyCollectionsUIState>
    fun submit(action: MyCollectionsAction)

    class Default(
        private val fetchMyCollectionsUseCase: FetchAllCollectionsAsTitledMediaUseCase,
        coroutineScope: CoroutineScope? = null // // nullable due to iOS
    ) : MLMyCollectionViewModelNew {

        private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main.immediate)

        private val _screenState = MutableStateFlow<MyCollectionsUIState>(Loading)
        override val state: CStateFlow<MyCollectionsUIState>
            get() = _screenState.cStateFlow()

        override fun submit(action: MyCollectionsAction) {
            println(action)
            when (action) {
                FetchMyCollectionsMedia -> {
                    viewModelScope.launch {
                        fetchMyCollectionsUseCase()
                            .onSuccess { _screenState.update { MyCollectionsContent } }
                            .onFailure { _screenState.update { FailedToFetchCollections } }
                    }
                }
            }
        }
    }
}

