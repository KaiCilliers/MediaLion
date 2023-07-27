package com.sunrisekcdeveloper.medialion.android.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.sunrisekcdeveloper.medialion.android.features.home.components.MLBottomNavBar
import com.zhuinden.simplestackcomposeintegration.core.LocalBackstack

@Composable
fun HomeScreen() {
    val currentBackStack = LocalBackstack.current

    var homeDestination: HomeDestinations by rememberSaveable { mutableStateOf(HomeDestinations.Discovery) }

    Column(Modifier.fillMaxSize()) {
        HomeDestinationContainer(
            selectedTab = homeDestination,
            parentBackStack = currentBackStack,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        )
        MLBottomNavBar(
            selectedTab = homeDestination,
            onDestinationChange = { newDest -> homeDestination = newDest }
        )
    }
}