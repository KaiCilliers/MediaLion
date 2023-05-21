package com.sunrisekcdeveloper.medialion.domain.discovery

import com.sunrisekcdeveloper.medialion.TitledMedia
import com.sunrisekcdeveloper.medialion.domain.search.usecases.FetchDiscoveryContent
import com.sunrisekcdeveloper.medialion.flow.CStateFlow
import com.sunrisekcdeveloper.medialion.flow.cStateFlow
import io.github.aakira.napier.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MLDiscoveryViewModel(
    private val fetchDiscoveryContent: FetchDiscoveryContent,
    coroutineScope: CoroutineScope?
) {

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main.immediate)

    private val _state = MutableStateFlow<DiscoveryState>(DiscoveryState.Loading)
    val state: CStateFlow<DiscoveryState>
        get() = _state.cStateFlow()

    fun submitAction(action: DiscoveryAction) {
        log { "Discovery - submitted an action $action" }
        when (action) {
            DiscoveryAction.FetchContent -> {
                viewModelScope.launch {
                    val cot = fetchDiscoveryContent()
                    log {  "deadpool - content - $cot" }
                    _state.value = DiscoveryState.Content(cot)
                }
            }
        }
    }

}

sealed class DiscoveryAction {
    object FetchContent : DiscoveryAction()
}

sealed class DiscoveryState {
    object Loading : DiscoveryState()
    data class Content(val media: List<TitledMedia>) : DiscoveryState()
    data class Error(val msg: String, val exception: Exception? = null): DiscoveryState()
}