package com.sunrisekcdeveloper.medialion.android.ui.home

import com.sunrisekcdeveloper.medialion.android.ui.components.ui.BottomBarOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel {

    private val _currentTab = MutableStateFlow(BottomBarOption.DISCOVERY)
    val currentTab = _currentTab.asStateFlow()

    fun setCurrentTab(tab: BottomBarOption) {
        _currentTab.value = tab
    }
}