package com.example.medialion.domain.components.search

import com.example.medialion.data.extensions.doIfFailure
import com.example.medialion.data.extensions.doIfSuccess
import com.example.medialion.domain.mappers.ListMapper
import com.example.medialion.domain.models.Movie
import com.example.medialion.domain.models.MovieUiModel
import com.example.medialion.flow.CStateFlow
import com.example.medialion.flow.cStateFlow
import com.example.medialion.flow.combineTuple
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class MLSearchViewModel(
    private val searchMoviesByTitleUseCase: SearchMoviesUseCase,
    private val relatedMoviesUseCase: RelatedMoviesUseCase,
    private val movieMapper: ListMapper<Movie, MovieUiModel>,
    coroutineScope: CoroutineScope?,
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val searchResults = MutableStateFlow(emptyList<MovieUiModel>())
    private val relatedMovies: Flow<List<MovieUiModel>> = searchResults
        .flatMapLatest { movies ->
            flow {
                if (movies.isNotEmpty()) {
                    val response = relatedMoviesUseCase.relateMovies(movies.first().id)
                    response.doIfSuccess { emit(movieMapper.map(it)) }
                    response.doIfFailure { _, _ -> emit(emptyList()) }
                }
            }
        }

    private val _state = combineTuple(
        searchResults,
        relatedMovies,
    ).map { (results, related) ->
        SearchState.Results(
            searchResults = results,
            relatedTitles = listOf(related)
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), SearchState.Loading)
    val state: CStateFlow<SearchState>
        get() = _state.cStateFlow()

    init {
        viewModelScope.launch {
            searchMoviesByTitleUseCase.searchMovies("shrek").also {
                it.doIfSuccess { movies -> searchResults.value = movieMapper.map(movies) }
                it.doIfFailure { _, _ ->  searchResults.value = emptyList() }
            }
        }
    }

    fun submitAction(action: SearchAction) {
        println("deadpool - $action")
    }
}

