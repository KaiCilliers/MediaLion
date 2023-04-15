package com.example.medialion.android.ui.search

import com.example.medialion.domain.components.search.SearchViewModel
import com.example.medialion.flow.CStateFlow
import com.example.medialion.flow.cStateFlow
import com.example.medialion.domain.components.search.SearchState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class ASearchViewModel {
    private val mViewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val innerViewModel by lazy { SearchViewModel(mViewModelScope) }

    val state: CStateFlow<SearchState> = innerViewModel.sate.cStateFlow()

    fun search(query: String) {
        innerViewModel.search(query)
    }
}