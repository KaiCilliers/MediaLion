package com.sunrisekcdeveloper.medialion.domain.collection

import com.sunrisekcdeveloper.medialion.MediaItemUI
import com.sunrisekcdeveloper.medialion.domain.MediaType
import com.sunrisekcdeveloper.medialion.domain.entities.CollectionWithMediaUI
import com.sunrisekcdeveloper.medialion.domain.entities.MediaItem
import com.sunrisekcdeveloper.medialion.domain.search.CollectionComponent
import com.sunrisekcdeveloper.medialion.domain.search.SearchComponent
import com.sunrisekcdeveloper.medialion.domain.value.Genre
import com.sunrisekcdeveloper.medialion.domain.value.ID
import com.sunrisekcdeveloper.medialion.domain.value.Title
import com.sunrisekcdeveloper.medialion.flow.CStateFlow
import com.sunrisekcdeveloper.medialion.flow.cStateFlow
import com.sunrisekcdeveloper.medialion.mappers.ListMapper
import io.github.aakira.napier.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MLCollectionViewModel(
    private val collectionComponent: CollectionComponent,
    private val searchComponent: SearchComponent,
    private val mediaListMapper: ListMapper<MediaItem, MediaItemUI>,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main.immediate)

    private val allCollections = collectionComponent.fetchAllCollections()
        .map { list ->
            list.map {
                CollectionWithMediaUI(
                    name = it.name, contents = mediaListMapper.map(it.contents)
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), emptyList())
    private val loading = MutableStateFlow(false)

    private val _state = combine(loading, allCollections) { isLoading, collections ->
        val state = when {
            isLoading -> CollectionState.Loading
            collections.isNotEmpty() -> CollectionState.AllCollections(collections)
            else -> CollectionState.Empty
        }
        log { "Collections - emitting a new state $state" }
        state
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), CollectionState.Empty)

    val state: CStateFlow<CollectionState>
        get() = _state.cStateFlow()

    private val _genres: MutableStateFlow<GenreState> = MutableStateFlow(GenreState.Genres(emptyList()))
    val genres: CStateFlow<GenreState> = _genres
        .onEach { log { "deadpool - got a list of genres $it" } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), GenreState.Genres(emptyList()))
        .cStateFlow()

    init {
        viewModelScope.launch {
            collectionComponent.fetchAllGenresUseCase().also {
                log { "Deadpool - Got genres ${it}" }
                _genres.value = GenreState.Genres(it)
            }
        }
    }

    fun submitAction(action: CollectionAction) {
        log { "Collections - submitted an action $action" }
        when (action) {
            is CollectionAction.AddToCollection -> {
                viewModelScope.launch {
                    collectionComponent.saveMediaToCollection(action.collectionName, action.mediaId, action.mediaType)
                }
            }
            is CollectionAction.CreateCollection -> {
                viewModelScope.launch {
                    collectionComponent.createCollection(action.collectionName)
                }
            }
            is CollectionAction.GetMediaDetails -> {
                viewModelScope.launch {
                    when (action.mediaType) {
                        MediaType.MOVIE -> searchComponent.movieDetails(action.mediaId)
                        MediaType.TV -> searchComponent.tvDetails(action.mediaId)
                    }
                }
            }
            is CollectionAction.RemoveFromCollection -> {
                viewModelScope.launch {
                    collectionComponent.removeMediaFromCollection(action.collectionName, action.mediaId, action.mediaType)
                }
            }

            is CollectionAction.RenameCollection -> {
                viewModelScope.launch {
                    collectionComponent.renameCollection(action.oldCollectionName, action.newCollectionName)
                }
            }
        }
    }

}

sealed class GenreState {
    data class Genres(val all: List<Genre>) : GenreState()
}

sealed class CollectionState {
    data class AllCollections(
        val collections: List<CollectionWithMediaUI>
    ) : CollectionState()
    object Loading : CollectionState()
    object Empty : CollectionState()
}

sealed class CollectionAction {
    data class GetMediaDetails(
        val mediaId: ID,
        val mediaType: MediaType,
    ): CollectionAction()
    data class RemoveFromCollection(
        val collectionName: Title,
        val mediaId: ID,
        val mediaType: MediaType,
    ): CollectionAction()
    data class AddToCollection(
        val collectionName: Title,
        val mediaId: ID,
        val mediaType: MediaType,
    ): CollectionAction()
    data class CreateCollection(
        val collectionName: Title
    ) : CollectionAction()
    data class RenameCollection(
        val oldCollectionName: Title,
        val newCollectionName: Title
    ) : CollectionAction()
}