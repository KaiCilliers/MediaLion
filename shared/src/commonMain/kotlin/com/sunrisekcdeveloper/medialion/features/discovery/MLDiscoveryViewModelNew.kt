package com.sunrisekcdeveloper.medialion.features.discovery

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.sunrisekcdeveloper.medialion.components.discovery.domain.FetchDiscoveryContentUseCase
import com.sunrisekcdeveloper.medialion.components.discovery.domain.FetchMediaWithCategoryUseCase
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.DiscoveryPage
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaCategory
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.TitledMediaList
import com.sunrisekcdeveloper.medialion.utils.debug
import com.sunrisekcdeveloper.medialion.utils.flow.CStateFlow
import com.sunrisekcdeveloper.medialion.utils.flow.cStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface MLDiscoveryViewModelNew {
    val discState: CStateFlow<DiscoveryUIState>
    fun submit(action: DiscoveryNewActions)

    class D(
        private val fetchDiscoveryContentUseCase: FetchDiscoveryContentUseCase,
        private val fetchMediaForCategoryUseCase: FetchMediaWithCategoryUseCase,
        coroutineScope: CoroutineScope?,
    ) : MLDiscoveryViewModelNew {

        private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main.immediate)

        private val _state = MutableStateFlow<DiscoveryUIState>(DiscoveryUIState.Loading(tabSelection = DiscoveryScreenTabSelection.All))
        override val discState: CStateFlow<DiscoveryUIState>
            get() = _state.cStateFlow()

        override fun submit(action: DiscoveryNewActions) {
            when (action) {
                is FetchMediaForCategory -> viewModelScope.launch {
                    val selectedTab = DiscoveryScreenTabSelection.Categories
                    _state.update { DiscoveryUIState.Loading(tabSelection = selectedTab) }

                    fetchMediaForCategoryUseCase(action.category)
                        .onSuccess { mediaWithTitle ->
                            _state.update {
                                DiscoveryUIState.Content(
                                    media = TitledMediaList.Def(listOf(mediaWithTitle)),
                                    // todo these tab states needs tests
                                    tabSelection = DiscoveryScreenTabSelection.Categories
                                )
                            }
                        }
                        .onFailure { _state.update { DiscoveryUIState.Error(tabSelection = DiscoveryScreenTabSelection.Categories) } }
                }

                is FetchPageMediaContent -> viewModelScope.launch {
                    val selectedTab = tabSelectionFor(action.page)
                    _state.update { DiscoveryUIState.Loading(tabSelection = selectedTab) }

                    fetchDiscoveryContentUseCase(action.page)
                        .onSuccess { titledMediaList ->
                            _state.update {
                                DiscoveryUIState.Content(
                                    media = titledMediaList,
                                    tabSelection = selectedTab
                                )
                            }
                        }
                        .onFailure { _state.update { DiscoveryUIState.Error(tabSelection = selectedTab) } }
                }
            }
        }

        private fun tabSelectionFor(page: DiscoveryPage): DiscoveryScreenTabSelection {
            return when (page) {
                DiscoveryPage.All -> DiscoveryScreenTabSelection.All
                DiscoveryPage.Movies -> DiscoveryScreenTabSelection.Movies
                DiscoveryPage.TVShows -> DiscoveryScreenTabSelection.TVShows
            }
        }
    }
}

sealed interface DiscoveryNewActions
// todo used nested hierarchy - it is easier for me to find all the actions that way
data class FetchPageMediaContent(val page: DiscoveryPage) : DiscoveryNewActions
data class FetchMediaForCategory(val category: MediaCategory) : DiscoveryNewActions

sealed class DiscoveryUIState(
    open val tabSelection: DiscoveryScreenTabSelection = DiscoveryScreenTabSelection.All,
) {
    data class Loading(
        override val tabSelection: DiscoveryScreenTabSelection,
    ) : DiscoveryUIState(tabSelection)
    data class Error(
        override val tabSelection: DiscoveryScreenTabSelection,
    ) : DiscoveryUIState(tabSelection)
    data class Content(
        val media: TitledMediaList,
        override val tabSelection: DiscoveryScreenTabSelection,
    ) : DiscoveryUIState(tabSelection)
}

sealed class DiscoveryScreenTabSelection {
    object All : DiscoveryScreenTabSelection()
    object Movies : DiscoveryScreenTabSelection()
    object TVShows : DiscoveryScreenTabSelection()
    object Categories : DiscoveryScreenTabSelection()
}