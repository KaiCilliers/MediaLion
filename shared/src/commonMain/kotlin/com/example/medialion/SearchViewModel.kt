package com.example.medialion

import com.example.medialion.flow.combineTuple
import com.example.medialion.models.MovieUiModel
import com.example.medialion.search.SearchState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

class SearchViewModel(
    private val coroutineScope: CoroutineScope?
) {
    private val mViewModelScope = coroutineScope ?: CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val currentQuery = MutableStateFlow("")
    private val isLoading = MutableStateFlow(false)

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private val searchResults: Flow<List<String>> = currentQuery
        .debounce(125L)
        .onEach { query ->
            if (query.isNotEmpty()) {
                isLoading.value = true
            }
        }
        .flatMapLatest { query ->
            when {
                query.isEmpty() -> emptyFlow()
                else -> flowOf(listOf("1","2"))
            }
        }
        .onEach { isLoading.value = false }
        .stateIn(mViewModelScope, SharingStarted.WhileSubscribed(5_000L), emptyList())

    val sate = combineTuple(
        currentQuery,
        isLoading,
        searchResults
    ).map { (query, isLoading, results) ->
     when {
         isLoading -> SearchState.Loading
         results.isNotEmpty() -> SearchState.Results(
             searchResults = listOf(MovieUiModel(1, "title", true)),
             relatedTitles = listOf(listOf(MovieUiModel(1, "title", true)))
         )
         results.isEmpty() -> SearchState.Empty
         else -> SearchState.Idle(suggestedMedia = listOf(MovieUiModel(1, "title", true)))
     }
    }.stateIn(mViewModelScope, SharingStarted.WhileSubscribed(5_000L), SearchState.Loading)

    fun search(query: String) {
        currentQuery.value = query
    }
}