package com.sunrisekcdeveloper.medialion.oldArch.domain.search

import com.sunrisekcdeveloper.medialion.oldArch.MediaItemUI
import com.sunrisekcdeveloper.medialion.oldArch.TitledMedia
import com.sunrisekcdeveloper.medialion.oldArch.domain.MediaType
import com.sunrisekcdeveloper.medialion.oldArch.domain.entities.MediaItem
import com.sunrisekcdeveloper.medialion.oldArch.domain.entities.Movie
import com.sunrisekcdeveloper.medialion.oldArch.domain.entities.TVShow
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.ID
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title
import com.sunrisekcdeveloper.medialion.oldArch.flow.CStateFlow
import com.sunrisekcdeveloper.medialion.oldArch.flow.cStateFlow
import com.sunrisekcdeveloper.medialion.oldArch.flow.combineTuple
import com.sunrisekcdeveloper.medialion.oldArch.mappers.ListMapper
import com.sunrisekcdeveloper.medialion.oldArch.mappers.Mapper
import io.github.aakira.napier.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
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
    private val tvListMapper: ListMapper<TVShow, MediaItemUI>,
    coroutineScope: CoroutineScope?,
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main.immediate)

    private val favoriteMovies: StateFlow<List<MediaItem>> = collectionComponent
        .fetchCollection(Title("Favorites"))
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
                query.isEmpty() -> {
                    log { "query is empty, so we return an empty flow" }
                    emptyFlow<List<MediaItemUI>>()
                }
                else -> {
                    log { "query is not empty $query" }
                    flow {
                        runCatching {
                            searchComponent.topMediaResultsUseCase(query)
                                .mapNotNull {
                                    runCatching {
                                        mediaItemMapper.map(it)
                                    }
                                        .onFailure { log { "failed to map an item ${it.message}, ${it.cause}" } }
                                        .getOrNull()
                                }
                                .toList()
                        }
                            .onSuccess {
                                emit(it)
                            }
                    }
                }
            }
        }
        .onEach {
            isLoading.value = false
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), listOf())

    private val relatedMovies: Flow<List<MediaItemUI>> = searchResults
        .flatMapLatest { media ->
            flow {
                media.sortedBy { it.popularity }.firstOrNull { it.mediaType == MediaType.MOVIE }?.let { sourceMovie ->
                    searchComponent.relatedMoviesUseCase(ID(sourceMovie.id))
                        .toList()
                        .also { relatedMovies ->
                            emit(movieListMapper.map(relatedMovies))
                        }
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val relatedTVShows: Flow<List<MediaItemUI>> = searchResults
        .flatMapLatest { media ->
            flow {
                media.sortedBy { it.popularity }.firstOrNull { it.mediaType == MediaType.TV }?.let { sourceTV ->
                    searchComponent.tvRelatedToUseCase(ID(sourceTV.id))
                        .toList()
                        .also { relatedTV ->
                            emit(tvListMapper.map(relatedTV))
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
        relatedTVShows,
        suggestedMovies,
        favoriteMovies,
    ).map { (query, isLoading, results, relatedMovies, relatedTV, suggestedMovies, favMovies) ->
        log {"tuple query=$query, isLoading=$isLoading, results=${results.size}, relatedMovies=${relatedMovies.size}, relatedTv=${relatedTV.size} suggested=${suggestedMovies.size}, fav=${favMovies.size}" }

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
                    TitledMedia(title = "Related Movies", content = relatedMovies.sortedBy { it.popularity }),
                    TitledMedia(title = "Related TV Shows", content = relatedTV.sortedBy { it.popularity }),
                ),
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
        log { "wolverine - submitted an action $action" }
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
        collectionComponent.saveMediaToCollection(Title("Favorites"), mediaId, mediaType)
    }

    private fun removeFromFavorites(mediaId: ID, mediaType: MediaType) = viewModelScope.launch {
        collectionComponent.removeMediaFromCollection(Title("Favorites"), mediaId, mediaType)
    }
}
