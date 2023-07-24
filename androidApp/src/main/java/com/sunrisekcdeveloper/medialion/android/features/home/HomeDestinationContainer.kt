package com.sunrisekcdeveloper.medialion.android.features.home

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sunrisekcdeveloper.medialion.android.features.collections.CollectionsKey
import com.sunrisekcdeveloper.medialion.android.features.discovery.DiscoveryKey
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
                        History.of(CollectionsKey),
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