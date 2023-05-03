package com.example.medialion.domain.components.search

import com.example.medialion.domain.components.saveToCollection.CollectionComponent
import com.example.medialion.domain.mappers.ListMapper
import com.example.medialion.domain.mappers.Mapper
import com.example.medialion.domain.models.MediaItem
import com.example.medialion.domain.models.MediaItemUI
import com.example.medialion.domain.models.Movie
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
    private val myCollectionComponent: CollectionComponent,
    private val mediaItemMapper: Mapper<MediaItem, MediaItemUI>,
    private val movieListMapper: ListMapper<Movie, MediaItemUI>,
    coroutineScope: CoroutineScope?,
) {
    private val viewModelScope =
        coroutineScope ?: CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val favoriteMovies = myCollectionComponent.favoriteMovieIds()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), emptyList())

    private val suggestedMovies = MutableStateFlow(emptyList<MediaItemUI>())
    private val currentQuery = MutableStateFlow("")
    private val isLoading = MutableStateFlow(false)
    private val searchResults: StateFlow<List<MediaItemUI>> = currentQuery
        .debounce(800L)
        .onEach {
            if (it.isNotEmpty()) isLoading.value = true
        }
        .flatMapLatest { query ->
            when {
                query.isEmpty() -> emptyFlow<List<MediaItemUI>>()
                else -> {
                    flow {
                        emit(
                            searchComponent.searchResults(query)
                                .map { mediaItemMapper.map(it) }
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

    private val relatedMovies: Flow<List<MediaItemUI>> = searchResults
        .flatMapLatest<List<MediaItemUI>, List<MediaItemUI>> { movies ->
            flow {
                if (movies.isNotEmpty()) {
                    searchComponent.relatedMovies(movies.first().id)
                        .toList()
                        .also { emit(movieListMapper.map(it)) }
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val _collectionsState = myCollectionComponent.allCollections()
    val allCollectionsState = _collectionsState
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList()
        ).cStateFlow()

    private val _state = combineTuple(
        currentQuery,
        isLoading,
        searchResults,
        relatedMovies,
        suggestedMovies,
        favoriteMovies,
    ).map { (query, isLoading, results, related, suggestedMovies, favMovies) ->
        println("deadpool - tuple $query, $isLoading, ${results.size}, ${related.size}, ${suggestedMovies.size}, ${favMovies.size}")

        println("spiderman - ${favMovies}")

        val mergedFavorites = suggestedMovies.map { suggestedMovie ->
            if (favMovies.contains(suggestedMovie.id)) {
                suggestedMovie.copy(isFavorited = true)
            } else suggestedMovie
        }

        when {
            query.isEmpty() -> SearchState.Idle(searchQuery = query, mergedFavorites)
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
            else -> SearchState.Idle(searchQuery = query, suggestedMovies)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        SearchState.Idle("", suggestedMovies.value)
    )

    init {
        viewModelScope.launch {
            val suggestedMedia = searchComponent.suggestedMedia()
                .take(30)
                .toList()
            suggestedMovies.value = movieListMapper.map(suggestedMedia)
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
            is SearchAction.GetMovieDetails -> {
                viewModelScope.launch {
                    searchComponent.detailsForMovie(action.movieId)
                }
            }

            is SearchAction.AddToCollection -> {
                viewModelScope.launch { myCollectionComponent.addMovieToCollection(action.collectionName, action.movieId) }
            }
            is SearchAction.CreateCollection -> {
                viewModelScope.launch {
                    myCollectionComponent.createCollection(action.collectionName)
                }
            }
            is SearchAction.RemoveFromCollection -> {
                viewModelScope.launch { myCollectionComponent.removeMovieFromCollection(action.collectionName, action.movieId) }
            }
        }
    }

    private  fun addToFavorites(movieId: Int) = viewModelScope.launch {
        myCollectionComponent.addMovieToFavorites(movieId)
    }

    private fun removeFromFavorites(movieId: Int) = viewModelScope.launch {
        myCollectionComponent.removeMovieFromFavorites(movieId)
    }
}
