package com.sunrisekcdeveloper.medialion.domain.discovery

import com.sunrisekcdeveloper.medialion.TitledMedia
import com.sunrisekcdeveloper.medialion.domain.MediaType
import com.sunrisekcdeveloper.medialion.domain.search.CollectionComponent
import com.sunrisekcdeveloper.medialion.domain.search.SearchComponent
import com.sunrisekcdeveloper.medialion.domain.search.usecases.FetchDiscoveryContent
import com.sunrisekcdeveloper.medialion.domain.value.ID
import com.sunrisekcdeveloper.medialion.domain.value.Title
import com.sunrisekcdeveloper.medialion.flow.CStateFlow
import com.sunrisekcdeveloper.medialion.flow.cStateFlow
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
    }

    fun submitAction(action: DiscoveryAction) {
        log { "Discovery - submitted an action $action" }
        when (action) {
            is DiscoveryAction.FetchContent -> {
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
        }
    }

    private fun addToFavorites(mediaId: ID, mediaType: MediaType) = viewModelScope.launch {
        collectionComponent.saveMediaToCollection(Title("favorite"), mediaId, mediaType)
    }

    private fun removeFromFavorites(mediaId: ID, mediaType: MediaType) = viewModelScope.launch {
        collectionComponent.removeMediaFromCollection(Title("favorite"), mediaId, mediaType)
    }
}

sealed class DiscoveryAction {
    data class FetchContent(val mediaType: Int) : DiscoveryAction()
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