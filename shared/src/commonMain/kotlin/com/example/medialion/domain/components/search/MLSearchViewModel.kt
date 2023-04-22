package com.example.medialion.domain.components.search

import com.example.medialion.data.extensions.doIfFailure
import com.example.medialion.data.extensions.doIfSuccess
import com.example.medialion.domain.mappers.ListMapper
import com.example.medialion.domain.models.Movie
import com.example.medialion.domain.models.MovieUiModel
import com.example.medialion.domain.models.ResultOf
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
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class MLSearchViewModel(
    private val searchMoviesByTitleUseCase: SearchMoviesUseCase,
    private val relatedMoviesUseCase: RelatedMoviesUseCase,
    private val movieMapper: ListMapper<Movie, MovieUiModel>,
    coroutineScope: CoroutineScope?,
) {
    private val viewModelScope =
        coroutineScope ?: CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val suggestedMovies = MutableStateFlow(SuggestedMovies.list.toList())
    private val currentQuery = MutableStateFlow("")
    private val isLoading = MutableStateFlow(false)
    private val searchResults: StateFlow<List<MovieUiModel>> = currentQuery
        .debounce(125L)
        .onEach {
            if (it.isNotEmpty()) isLoading.value = true
        }
        .flatMapLatest { query ->
            println("deadpool - query changed -> $query")
            when {
                query.isEmpty() -> emptyFlow<List<MovieUiModel>>()
                else -> {
                    when (val response = searchMoviesByTitleUseCase.searchMovies(query)) {
                        is ResultOf.Failure -> {
                            println("deadpool - failed")
                            emptyFlow()
                        }

                        is ResultOf.Success -> {
                            println("deadpool - success")
                            flow { emit(movieMapper.map(response.value)) }
                        }
                    }
                }
            }
        }.onEach {
            println("deadpool - loasing false")
            isLoading.value = false
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), listOf())

    private val relatedMovies: Flow<List<MovieUiModel>> = searchResults
        .flatMapLatest<List<MovieUiModel>, List<MovieUiModel>> { movies ->
            println("deadpool - inside related")
            flow {
                if (movies.isNotEmpty()) {
                    val response = relatedMoviesUseCase.relateMovies(movies.first().id)
                    response.doIfSuccess { emit(movieMapper.map(it)) }
                    response.doIfFailure { _, _ -> emit(emptyList()) }
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
                relatedTitles = listOf(related.sortedBy { it.title }, related.sortedBy { it.posterUrl }, related.sortedBy { it.id }),
                searchQuery = query
            )

            query.isNotEmpty() && results.isEmpty() -> SearchState.Empty(query)
            // TODO error state for search screen
            else -> SearchState.Idle(searchQuery = query, SuggestedMovies.list.take(2))
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        SearchState.Idle("", SuggestedMovies.list.toList())
    )

    init {
        // TODO fix issue where this whole class starts kicking into gear when the search query changes for the first time
        //  until then, toggling favorited movies won't show in the UI
        //  currentQuery.value = "" does not fix the issue (passing it a value does)
        currentQuery.value = ""
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
        suggestedMovies.value = SuggestedMovies.list.map {
            if (it.id == movieId) {
                it.copy(isFavorited = true)
            } else {
                it
            }
        }.also {
            SuggestedMovies.list.clear()
            SuggestedMovies.list.addAll(it)
        }
    }

    private fun removeFromFavorites(movieId: Int) {
        suggestedMovies.value = SuggestedMovies.list.map {
            if (it.id == movieId) {
                it.copy(isFavorited = false)
            } else {
                it
            }
        }.also {
            SuggestedMovies.list.clear()
            SuggestedMovies.list.addAll(it)
        }
    }
}

private object SuggestedMovies {
    val list = mutableListOf(
        MovieUiModel(
            id = 97551,
            title = "Movie #1",
            isFavorited = false,
            posterUrl = "https://image.tmdb.org/t/p/original/hziiv14OpD73u9gAak4XDDfBKa2.jpg"
        ),
        MovieUiModel(
            id = 97553,
            title = "Movie #2",
            isFavorited = false,
            posterUrl = "xx"
        ),
        MovieUiModel(
            id = 975544,
            title = "Movie #3",
            isFavorited = false,
            posterUrl = "https://image.tmdb.org/t/p/original/hziiv14OpD73u9gAak4XDDfBKa2.jpg"
        ),
        MovieUiModel(
            id = 971551,
            title = "Movie #4",
            isFavorited = false,
            posterUrl = "https://image.tmdb.org/t/p/original/hziiv14OpD73u9gAak4XDDfBKa2.jpg"
        ),
        MovieUiModel(
            id = 9713552,
            title = "Movie #5",
            isFavorited = false,
            posterUrl = "xx"
        ),
        MovieUiModel(
            id = 9713553,
            title = "Movie #7",
            isFavorited = false,
            posterUrl = "https://image.tmdb.org/t/p/original/hziiv14OpD73u9gAak4XDDfBKa2.jpg"
        ),
        MovieUiModel(
            id = 9713554,
            title = "Movie #8",
            isFavorited = false,
            posterUrl = "https://image.tmdb.org/t/p/original/hziiv14OpD73u9gAak4XDDfBKa2.jpg"
        ),
        MovieUiModel(
            id = 9713555,
            title = "Movie #9",
            isFavorited = false,
            posterUrl = "xx"
        ),
        MovieUiModel(
            id = 9713556,
            title = "Movie #10",
            isFavorited = false,
            posterUrl = "https://image.tmdb.org/t/p/original/hziiv14OpD73u9gAak4XDDfBKa2.jpg"
        ),
    )
}