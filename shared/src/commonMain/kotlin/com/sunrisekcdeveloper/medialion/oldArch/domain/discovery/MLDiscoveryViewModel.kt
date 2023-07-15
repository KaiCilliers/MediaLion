package com.sunrisekcdeveloper.medialion.oldArch.domain.discovery

import FetchDiscoveryContent
import com.sunrisekcdeveloper.medialion.oldArch.TitledMedia
import com.sunrisekcdeveloper.medialion.oldArch.domain.MediaType
import com.sunrisekcdeveloper.medialion.oldArch.domain.search.CollectionComponent
import com.sunrisekcdeveloper.medialion.oldArch.domain.search.SearchComponent
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.ID
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title
import com.sunrisekcdeveloper.medialion.oldArch.flow.CStateFlow
import com.sunrisekcdeveloper.medialion.oldArch.flow.cStateFlow
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.github.aakira.napier.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MLDiscoveryViewModel(
    private val fetchDiscoveryContent: FetchDiscoveryContent,
    private val collectionComponent: CollectionComponent,
    private val searchComponent: SearchComponent,
    coroutineScope: CoroutineScope?
) {

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main.immediate)

    private val _state = MutableStateFlow<DiscoveryState>(DiscoveryState.Loading)
    val state: CStateFlow<DiscoveryState>
        get() = _state.cStateFlow()

    private val _collectionsState = collectionComponent.fetchAllCollections()
    val allCollectionsState = _collectionsState
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList()
        ).cStateFlow()

    init {
        Napier.base(DebugAntilog())
        // TODO find better place to setup initial collections
        viewModelScope.launch { collectionComponent.setupInitialCollections() }
    }

    fun submitAction(action: DiscoveryAction) {
        log { "Discovery - submitted an action $action" }
        when (action) {
            is DiscoveryAction.FetchContent -> {
                _state.value = DiscoveryState.Loading
                viewModelScope.launch {
                    val cot = fetchDiscoveryContent(action.mediaType)
                    _state.value = DiscoveryState.Content(cot)
                }
            }

            is DiscoveryAction.AddToFavorites -> addToFavorites(action.mediaId, action.mediaType)
            is DiscoveryAction.RemoveFromFavorites -> removeFromFavorites(
                action.movieId,
                action.mediaType
            )

            is DiscoveryAction.GetMediaDetails -> {
                viewModelScope.launch {
                    when (action.mediaType) {
                        MediaType.MOVIE -> searchComponent.movieDetails(action.mediaId)
                        MediaType.TV -> searchComponent.tvDetails(action.mediaId)
                    }
                }
            }

            is DiscoveryAction.AddToCollection -> {
                viewModelScope.launch {
                    collectionComponent.saveMediaToCollection(
                        action.collectionName,
                        action.mediaId,
                        action.mediaType
                    )
                }
            }

            is DiscoveryAction.CreateCollection -> {
                viewModelScope.launch {
                    collectionComponent.createCollection(action.collectionName)
                }
            }

            is DiscoveryAction.RemoveFromCollection -> {
                viewModelScope.launch {
                    collectionComponent.removeMediaFromCollection(
                        action.collectionName,
                        action.mediaId,
                        action.mediaType
                    )
                }
            }

            is DiscoveryAction.FetchGenreContent -> {
                viewModelScope.launch {
                    _state.value = DiscoveryState.Loading
                    viewModelScope.launch {
                        val cot = fetchDiscoveryContent(action.mediaType, action.genreId)
                        _state.value = DiscoveryState.Content(cot)
                    }
                }
            }
        }
    }

    private fun addToFavorites(mediaId: ID, mediaType: MediaType) = viewModelScope.launch {
        collectionComponent.saveMediaToCollection(Title("Favorites"), mediaId, mediaType)
    }

    private fun removeFromFavorites(mediaId: ID, mediaType: MediaType) = viewModelScope.launch {
        collectionComponent.removeMediaFromCollection(Title("Favorites"), mediaId, mediaType)
    }
}

sealed class DiscoveryAction {
    data class FetchContent(val mediaType: MediaType?) : DiscoveryAction()
    data class FetchGenreContent(val genreId: ID, val mediaType: MediaType) : DiscoveryAction()
    data class RemoveFromFavorites(
        val movieId: ID,
        val mediaType: MediaType,
    ) : DiscoveryAction()

    data class AddToFavorites(
        val mediaId: ID,
        val mediaType: MediaType,
    ) : DiscoveryAction()

    data class GetMediaDetails(
        val mediaId: ID,
        val mediaType: MediaType,
    ) : DiscoveryAction()

    data class RemoveFromCollection(
        val collectionName: Title,
        val mediaId: ID,
        val mediaType: MediaType,
    ) : DiscoveryAction()

    data class AddToCollection(
        val collectionName: Title,
        val mediaId: ID,
        val mediaType: MediaType,
    ) : DiscoveryAction()

    data class CreateCollection(
        val collectionName: Title
    ) : DiscoveryAction()
}

sealed class DiscoveryState {
    object Loading : DiscoveryState()
    data class Content(val media: List<TitledMedia>) : DiscoveryState()
    data class Error(val msg: String, val exception: Exception? = null) : DiscoveryState()
}