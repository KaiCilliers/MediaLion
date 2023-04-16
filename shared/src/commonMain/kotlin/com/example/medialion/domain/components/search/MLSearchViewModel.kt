package com.example.medialion.domain.components.search

import com.example.medialion.flow.CStateFlow
import com.example.medialion.flow.cStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow

class MLSearchViewModel(
    private val coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    val state: CStateFlow<SearchState> = MutableStateFlow(SearchState.Loading).cStateFlow()

    fun submitAction(action: SearchAction) {
        println("deadpool - $action")
    }
}