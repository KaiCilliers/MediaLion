package com.sunrisekcdeveloper.medialion.features.discovery

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaCategory
import com.sunrisekcdeveloper.medialion.components.shared.domain.FetchAllMediaCategoriesUseCase
import com.sunrisekcdeveloper.medialion.utils.flow.CStateFlow
import com.sunrisekcdeveloper.medialion.utils.flow.cStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface MLCategoriesViewModel {
    val catState: CStateFlow<CategoriesUIState>
    fun submit(action: CategoriesAction)

    class D(
        private val fetchAllMediaCategoriesUseCase: FetchAllMediaCategoriesUseCase,
        coroutineScope: CoroutineScope?,
    ) : MLCategoriesViewModel {

        private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main.immediate)

        private val _state: MutableStateFlow<CategoriesUIState> = MutableStateFlow(CategoriesUIState.Loading)
        override val catState: CStateFlow<CategoriesUIState>
            get() = _state.cStateFlow()

        override fun submit(action: CategoriesAction) {
            println("Processing $action")
            when (action) {
                FetchAllCategories -> viewModelScope.launch {
                    fetchAllMediaCategoriesUseCase()
                        .onSuccess { mediaCategories -> _state.update { CategoriesUIState.Content(mediaCategories) } }
                        .onFailure { _state.update { CategoriesUIState.Error } }
                }
            }
        }
    }
}

sealed interface CategoriesAction
object FetchAllCategories : CategoriesAction

sealed interface CategoriesUIState {
    object Loading : CategoriesUIState
    object Error : CategoriesUIState
    data class Content(val categories: List<MediaCategory>) : CategoriesUIState
}