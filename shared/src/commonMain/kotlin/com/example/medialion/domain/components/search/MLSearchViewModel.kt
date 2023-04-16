package com.example.medialion.domain.components.search

import com.example.medialion.data.extensions.doIfFailure
import com.example.medialion.data.extensions.doIfSuccess
import com.example.medialion.data.searchComponent.TMDBClient
import com.example.medialion.domain.models.MovieUiModel
import com.example.medialion.flow.CStateFlow
import com.example.medialion.flow.cStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MLSearchViewModel(
    private val client: TMDBClient,
    coroutineScope: CoroutineScope?,
) {
    private val viewModelScope =
        coroutineScope ?: CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var _state: MutableStateFlow<SearchState> = MutableStateFlow(SearchState.Loading)
    val state: CStateFlow<SearchState>
        get() = _state.cStateFlow()

    init {
        viewModelScope.launch {
            client.searchMovies("shrek").also { result ->
                result.doIfSuccess {
                    _state.value = SearchState.Results(
                        searchResults = it.results.map { mediaItem ->
                            MovieUiModel(
                                id = mediaItem.id,
                                title = mediaItem.title,
                                isFavorited = false,
                                posterUrl = mediaItem.posterPath.orEmpty()
                            )
                        },
                        relatedTitles = emptyList()
                    )
                }
                result.doIfFailure { _, _ ->
                    _state.value = SearchState.Empty
                }
            }
        }
    }

    fun submitAction(action: SearchAction) {
        println("deadpool - $action")
    }
}