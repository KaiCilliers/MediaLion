package com.example.medialion.domain.components.search

import com.example.medialion.domain.mappers.ListMapper
import com.example.medialion.domain.mappers.Mapper
import com.example.medialion.domain.models.Movie
import com.example.medialion.domain.models.MovieUiModel
import com.example.medialion.flow.CStateFlow
import com.example.medialion.flow.cStateFlow
import com.example.medialion.flow.combineTuple
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class MLSearchViewModel(
    private val searchComponent: SearchComponent,
    private val movieMapper: Mapper<Movie, MovieUiModel>,
    private val movieListMapper: ListMapper<Movie, MovieUiModel>,
    coroutineScope: CoroutineScope?,
) {

    private val suggestedMovieCache = mutableListOf<MovieUiModel>()

    private val viewModelScope =
        coroutineScope ?: CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val suggestedMovies = MutableStateFlow(emptyList<MovieUiModel>())
    private val currentQuery = MutableStateFlow("")
    private val isLoading = MutableStateFlow(false)
    private val searchResults: StateFlow<List<MovieUiModel>> = currentQuery
        .debounce(800L)
        .onEach {
            if (it.isNotEmpty()) isLoading.value = true
        }
        .flatMapLatest { query ->
            when {
                query.isEmpty() -> emptyFlow<List<MovieUiModel>>()
                else -> {
                    flow {
                        emit(
                            searchComponent.searchMovies(query)
                                .map { movieMapper.map(it) }
                                .toList()
                        )
                    }
                }
            }
        }
        .onEach {
            isLoading.value = false
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), listOf())

    private val relatedMovies: Flow<List<MovieUiModel>> = searchResults
        .flatMapLatest<List<MovieUiModel>, List<MovieUiModel>> { movies ->
            flow {
                if (movies.isNotEmpty()) {
                    searchComponent.relatedMovies(movies.first().id)
                        .toList()
                        .also { emit(movieListMapper.map(it)) }
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val _state = combineTuple(
        currentQuery,
        isLoading,
        searchResults,
        relatedMovies,
        suggestedMovies,
    ).map { (query, isLoading, results, related, suggestedMovies) ->
        println("deadpool - tuple $query, $isLoading, ${results.size}, ${related.size}, ${suggestedMovies.size}")
        when {
            query.isEmpty() -> SearchState.Idle(searchQuery = query, suggestedMovies)
            isLoading -> SearchState.Loading(query)
            results.isNotEmpty() -> SearchState.Results(
                searchResults = results,
                relatedTitles = listOf(
                    related.sortedBy { it.title },
                    related.sortedBy { it.posterUrl },
                    related.sortedBy { it.id }),
                searchQuery = query
            )

            query.isNotEmpty() && results.isEmpty() -> SearchState.Empty(query)
            // TODO error state for search screen
            else -> SearchState.Idle(searchQuery = query, suggestedMovieCache.take(2))
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        SearchState.Idle("", suggestedMovieCache.toList())
    )

    init {
        viewModelScope.launch {
            val suggestedMedia = searchComponent.suggestedMedia()
                .take(30)
                .toList()

            suggestedMovieCache.clear()
            suggestedMovieCache.addAll(movieListMapper.map(suggestedMedia))
            suggestedMovies.value = suggestedMovieCache
        }
    }

    val state: CStateFlow<SearchState>
        get() = _state.cStateFlow()

    fun submitAction(action: SearchAction) {
        when (action) {
            is SearchAction.AddToFavorites -> addToFavorites(action.movieId)
            SearchAction.ClearSearchText -> currentQuery.value = ""
            is SearchAction.RemoveFromFavorites -> removeFromFavorites(action.movieId)
            is SearchAction.SubmitSearchQuery -> currentQuery.value = action.query
        }
    }

    private fun addToFavorites(movieId: Int) {
        suggestedMovies.value = suggestedMovieCache.map {
            if (it.id == movieId) {
                it.copy(isFavorited = true)
            } else {
                it
            }
        }.also {
            suggestedMovieCache.clear()
            suggestedMovieCache.addAll(it)
        }
    }

    private fun removeFromFavorites(movieId: Int) {
        suggestedMovies.value = suggestedMovieCache.map {
            if (it.id == movieId) {
                it.copy(isFavorited = false)
            } else {
                it
            }
        }.also {
            suggestedMovieCache.clear()
            suggestedMovieCache.addAll(it)
        }
    }
}
