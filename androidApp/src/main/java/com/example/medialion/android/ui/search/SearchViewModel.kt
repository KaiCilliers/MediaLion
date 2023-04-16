package com.example.medialion.android.ui.search

import com.example.medialion.domain.components.search.MLSearchViewModel
import com.example.medialion.domain.components.search.SearchAction
import com.example.medialion.flow.CStateFlow
import com.example.medialion.flow.cStateFlow
import com.example.medialion.domain.components.search.SearchState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow

class SearchViewModel {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val viewModel by lazy { MLSearchViewModel(viewModelScope) }

    val state: StateFlow<SearchState> = viewModel.state

    fun submitAction(action: SearchAction) {
        viewModel.submitAction(action)
    }
}