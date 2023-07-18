package com.sunrisekcdeveloper.medialion.features.discovery

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.sunrisekcdeveloper.medialion.components.discovery.domain.FetchDiscoveryContentUseCase
import com.sunrisekcdeveloper.medialion.components.discovery.domain.FetchMediaWithCategoryUseCase
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.DiscoveryPage
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaCategory
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.TitledMediaList
import com.sunrisekcdeveloper.medialion.oldArch.domain.MediaType
import com.sunrisekcdeveloper.medialion.oldArch.domain.discovery.DiscoveryAction
import com.sunrisekcdeveloper.medialion.oldArch.domain.discovery.DiscoveryState
import com.sunrisekcdeveloper.medialion.oldArch.flow.CStateFlow
import com.sunrisekcdeveloper.medialion.oldArch.flow.cStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface MLDiscoveryViewModelNew {
    val state: CStateFlow<DiscoveryUIState>
    fun submit(action: DiscoveryNewActions)

    class D(
        private val fetchDiscoveryContentUseCase: FetchDiscoveryContentUseCase,
        private val fetchMediaForCategoryUseCase: FetchMediaWithCategoryUseCase,
        coroutineScope: CoroutineScope?,
    ) : MLDiscoveryViewModelNew {

        private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main.immediate)

        private val _state = MutableStateFlow<DiscoveryUIState>(Loading)
        override val state: CStateFlow<DiscoveryUIState>
            get() = _state.cStateFlow()

        override fun submit(action: DiscoveryNewActions) {
            when (action) {
                is FetchMediaForCategory -> viewModelScope.launch {
                    fetchMediaForCategoryUseCase(action.category)
                        .onSuccess { mediaWithTitle -> _state.update { Content(TitledMediaList.Def(listOf(mediaWithTitle))) } }
                        .onFailure { _state.update { Error } }
                }
                is FetchPageMediaContent -> viewModelScope.launch {
                    fetchDiscoveryContentUseCase(action.page)
                        .onSuccess { titledMediaList -> _state.update { Content(titledMediaList) } }
                        .onFailure { _state.update { Error } }
                }
            }
        }
    }
}

sealed interface DiscoveryNewActions
data class FetchPageMediaContent(val page: DiscoveryPage) : DiscoveryNewActions
data class FetchMediaForCategory(val category: MediaCategory) : DiscoveryNewActions

sealed interface DiscoveryUIState
object Loading : DiscoveryUIState
object Error : DiscoveryUIState
data class Content(val media: TitledMediaList) : DiscoveryUIState