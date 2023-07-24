package com.sunrisekcdeveloper.medialion.android.features.discovery

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.sunrisekcdeveloper.medialion.android.app.ComposeKey
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Immutable
@Parcelize
data object DiscoveryKey : ComposeKey() {
    @Composable
    override fun ScreenComposable(modifier: Modifier) {
        DiscoveryScreen()
    }
}