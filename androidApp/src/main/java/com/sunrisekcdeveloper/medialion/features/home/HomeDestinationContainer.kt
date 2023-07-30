package com.sunrisekcdeveloper.medialion.features.home

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sunrisekcdeveloper.medialion.features.discovery.DiscoveryKey
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.History
import com.zhuinden.simplestackcomposeintegration.core.ComposeNavigator
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider

@Composable
fun HomeDestinationContainer(
    selectedTab: HomeDestinations,
    parentBackStack: Backstack,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
    ){
        when (selectedTab) {
            HomeDestinations.Collections -> {
                ComposeNavigator(id = "collections") {
                    createBackstack(
                        History.of(com.sunrisekcdeveloper.medialion.features.collections.CollectionsKey),
                        scopedServices = DefaultServiceProvider(),
                        parentBackstack = parentBackStack
                    )
                }
            }
            HomeDestinations.Discovery ->  {
                ComposeNavigator(id = "discovery") {
                    createBackstack(
                        History.of(DiscoveryKey),
                        scopedServices = DefaultServiceProvider(),
                        parentBackstack = parentBackStack
                    )
                }
            }
        }
    }
}