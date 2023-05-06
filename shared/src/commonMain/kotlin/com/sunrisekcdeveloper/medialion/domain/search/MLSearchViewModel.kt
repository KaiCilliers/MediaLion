package com.sunrisekcdeveloper.medialion.domain.search

import com.sunrisekcdeveloper.medialion.MediaItemUI
import com.sunrisekcdeveloper.medialion.domain.MediaType
import com.sunrisekcdeveloper.medialion.domain.entities.MediaItem
import com.sunrisekcdeveloper.medialion.domain.entities.Movie
import com.sunrisekcdeveloper.medialion.domain.value.ID
import com.sunrisekcdeveloper.medialion.domain.value.Title
import com.sunrisekcdeveloper.medialion.flow.CStateFlow
import com.sunrisekcdeveloper.medialion.flow.cStateFlow
import com.sunrisekcdeveloper.medialion.flow.combineTuple
import com.sunrisekcdeveloper.medialion.mappers.ListMapper
import com.sunrisekcdeveloper.medialion.mappers.Mapper
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
    private val collectionComponent: CollectionComponent,
    private val mediaItemMapper: Mapper<MediaItem, MediaItemUI>,
    private val movieListMapper: ListMapper<Movie, MediaItemUI>,
    coroutineScope: CoroutineScope?,
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val favoriteMovies: StateFlow<List<MediaItem>> = collectionComponent
        .fetchCollection(Title("favorite"))
        .map { it.contents }
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
                            searchComponent.topMediaResultsUseCase(query)
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
                    searchComponent.relatedMoviesUseCase(ID(movies.minByOrNull { it.popularity }!!.id))
                        .toList()
                        .also { relatedMovies ->
                            emit(movieListMapper.map(relatedMovies))
                        }
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val _collectionsState = collectionComponent.fetchAllCollections()
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
            if (favMovies.map { it.id.value }.contains(suggestedMovie.id)) {
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
            val suggestedMedia = searchComponent.suggestedMediaUseCase()
                .take(30)
                .toList()
            suggestedMovies.value = movieListMapper.map(suggestedMedia)
        }
    }

    val state: CStateFlow<SearchState>
        get() = _state.cStateFlow()

    fun submitAction(action: SearchAction) {
        when (action) {
            is SearchAction.AddToFavorites -> addToFavorites(action.mediaId, action.mediaType)
            SearchAction.ClearSearchText -> currentQuery.value = ""
            is SearchAction.RemoveFromFavorites -> removeFromFavorites(action.movieId, action.mediaType)
            is SearchAction.SubmitSearchQuery -> currentQuery.value = action.query
            is SearchAction.GetMediaDetails -> {
                viewModelScope.launch {
                    when (action.mediaType) {
                        MediaType.MOVIE -> searchComponent.movieDetails(action.mediaId)
                        MediaType.TV -> searchComponent.tvDetails(action.mediaId)
                    }
                }
            }

            is SearchAction.AddToCollection -> {
                viewModelScope.launch {
                    collectionComponent.saveMediaToCollection(action.collectionName, action.mediaId, action.mediaType)
                }
            }
            is SearchAction.CreateCollection -> {
                viewModelScope.launch {
                    collectionComponent.createCollection(action.collectionName)
                }
            }
            is SearchAction.RemoveFromCollection -> {
                viewModelScope.launch {
                    collectionComponent.removeMediaFromCollection(action.collectionName, action.mediaId, action.mediaType)
                }
            }
        }
    }

    private  fun addToFavorites(mediaId: ID, mediaType: MediaType) = viewModelScope.launch {
        collectionComponent.saveMediaToCollection(Title("favorite"), mediaId, mediaType)
    }

    private fun removeFromFavorites(mediaId: ID, mediaType: MediaType) = viewModelScope.launch {
        collectionComponent.removeMediaFromCollection(Title("favorite"), mediaId, mediaType)
    }
}
