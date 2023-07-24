package com.sunrisekcdeveloper.medialion.features.search

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.github.michaelbull.result.runCatching
import com.sunrisekcdeveloper.medialion.components.discovery.domain.FailedToFetchFeatureMedia
import com.sunrisekcdeveloper.medialion.utils.flow.CStateFlow
import com.sunrisekcdeveloper.medialion.utils.flow.cStateFlow
import com.sunrisekcdeveloper.medialion.components.discovery.domain.Failure
import com.sunrisekcdeveloper.medialion.components.discovery.domain.FetchSuggestedMediaUseCase
import com.sunrisekcdeveloper.medialion.components.discovery.domain.NoMediaFound
import com.sunrisekcdeveloper.medialion.components.discovery.domain.SearchForMediaUseCase
import com.sunrisekcdeveloper.medialion.components.discovery.domain.SearchQueryNotReady
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.SearchQuery
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.MediaWithTitle
import com.sunrisekcdeveloper.medialion.utils.StringRes
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// todo this class has to separated from the domain "search component"
interface MLSearchViewModelNew {
    val searchState: CStateFlow<SearchUIState>
    fun submit(action: SearchScreenAction)

    class Default(
        private val searchForMediaUseCase: SearchForMediaUseCase,
        private val fetchSuggestedMediaUseCase: FetchSuggestedMediaUseCase,
        coroutineScope: CoroutineScope? // nullable due to iOS
    ) : MLSearchViewModelNew {

        private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main.immediate)

        private val _screenState = MutableStateFlow<SearchUIState>(SearchUIState.Loading())
        override val searchState: CStateFlow<SearchUIState>
            get() = _screenState.cStateFlow()


        init {
            submit(SearchScreenAction.FetchSuggestedMedia(SearchQuery.Default("")))
        }

        override fun submit(action: SearchScreenAction) {
            println("Processing $action")
            when (action) {
                is SearchScreenAction.SubmitSearchQuery -> search(action.query)
                is SearchScreenAction.FetchSuggestedMedia -> fetchSuggestedMedia(action.query)
            }
        }

        private fun fetchSuggestedMedia(currentQuery: SearchQuery) = viewModelScope.launch {
            fetchSuggestedMediaUseCase()
                .onSuccess { mediaWithTitle ->
                    _screenState.update { SearchUIState.TopSuggestions(currentQuery, mediaWithTitle.media()) }
                }
                .onFailure { error -> throw Exception("Failed to fetch suggested media") }
        }

        private fun search(query: SearchQuery) = viewModelScope.launch {
            if (query.canPerformQuery()) {
                _screenState.update { SearchUIState.Loading(query) }
                searchForMediaUseCase(query)
                    .onSuccess { results ->
                        _screenState.update { SearchUIState.Results(query, results) }
                    }
                    .onFailure {  error ->
                        when (error) {
                            NoMediaFound -> _screenState.update { SearchUIState.NoResults(query) }
                            is Failure -> throw Exception("Search Screen Failure!.", error.throwable)
                            SearchQueryNotReady -> TODO()
                        }
                    }
            } else {
                _screenState.update {
                    when(val currentState = _screenState.value) {
                        is SearchUIState.Loading -> SearchUIState.Loading(SearchQuery.Default(query.toString()))
                        is SearchUIState.NoResults -> SearchUIState.NoResults(SearchQuery.Default(query.toString()))
                        is SearchUIState.Results -> SearchUIState.Results(SearchQuery.Default(query.toString()), currentState.results)
                        is SearchUIState.TopSuggestions -> SearchUIState.TopSuggestions(SearchQuery.Default(query.toString()), currentState.media)
                    }
                }
            }
        }
    }
}

