package com.example.medialion.domain.components.search

import com.example.medialion.data.extensions.doIfFailure
import com.example.medialion.data.extensions.doIfSuccess
import com.example.medialion.domain.mappers.ListMapper
import com.example.medialion.domain.models.Movie
import com.example.medialion.domain.models.MovieUiModel
import com.example.medialion.flow.CStateFlow
import com.example.medialion.flow.cStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MLSearchViewModel(
    private val searchMoviesByTitle: SearchMoviesUseCase,
    private val movieMapper: ListMapper<Movie, MovieUiModel>,
    coroutineScope: CoroutineScope?,
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var _state: MutableStateFlow<SearchState> = MutableStateFlow(SearchState.Loading)
    val state: CStateFlow<SearchState>
        get() = _state.cStateFlow()

    init {
        viewModelScope.launch {
            searchMoviesByTitle.searchMovies("shrek").also {
                it.doIfSuccess { movies ->
                    _state.value = SearchState.Results(
                        searchResults = movieMapper.map(movies),
                        relatedTitles = emptyList()
                    )
                }
                it.doIfFailure { _, _ ->  _state.value = SearchState.Empty}
            }
        }
    }

    fun submitAction(action: SearchAction) {
        println("deadpool - $action")
    }
}

