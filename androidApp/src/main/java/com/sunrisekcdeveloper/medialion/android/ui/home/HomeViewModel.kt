package com.sunrisekcdeveloper.medialion.android.ui.home

import com.sunrisekcdeveloper.medialion.android.ui.components.ui.BottomBarOption
import com.sunrisekcdeveloper.medialion.android.ui.discovery.ui.FilterCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel {

    private val _currentTab = MutableStateFlow(BottomBarOption.DISCOVERY)
    val currentTab = _currentTab.asStateFlow()

    private val _currentTopTab = MutableStateFlow(FilterCategory.All)
    val currentTopTab = _currentTopTab.asStateFlow()

    fun setCurrentTab(tab: BottomBarOption) {
        _currentTab.value = tab
    }

    fun setTopTab(tab: FilterCategory) {
        _currentTopTab.value = tab
    }
}