package com.sunrisekcdeveloper.medialion.newarch.features.search

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.sunrisekcdeveloper.medialion.oldArch.flow.CStateFlow
import com.sunrisekcdeveloper.medialion.oldArch.flow.cStateFlow
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.Failure
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.NoMediaFound
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.SearchForMediaUseCase
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.SearchQueryNotReady
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// todo this class has to separated from the domain "search component"
interface MLSearchViewModelNew {

    val state: CStateFlow<SearchUIState>

    fun submitAction(action: SearchScreenAction)

    class Default(
        private val searchForMediaUseCase: SearchForMediaUseCase,
        coroutineScope: CoroutineScope? // nullable due to iOS
    ) : MLSearchViewModelNew {

        private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main.immediate)

        private val _screenState = MutableStateFlow<SearchUIState>(SearchUIState.TopSuggestions)
        override val state: CStateFlow<SearchUIState>
            get() = _screenState.cStateFlow()

        override fun submitAction(action: SearchScreenAction) {
            when (action) {
                is SearchScreenAction.SubmitSearchQuery -> {
                    viewModelScope.launch {
                        if (action.query.canPerformQuery()) {
                            _screenState.update { SearchUIState.Loading }
                            searchForMediaUseCase(action.query)
                                .onSuccess { results ->
                                    _screenState.update { SearchUIState.Results(results) }
                                }
                                .onFailure {  error ->
                                    when (error) {
                                        NoMediaFound -> _screenState.update { SearchUIState.NoResults }
                                        is Failure -> TODO()
                                        SearchQueryNotReady -> TODO()
                                    }
                                }
                        }
                    }
                }
            }
        }
    }
}

