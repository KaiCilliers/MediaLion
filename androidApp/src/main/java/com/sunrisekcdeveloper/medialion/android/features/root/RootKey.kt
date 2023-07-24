package com.sunrisekcdeveloper.medialion.android.features.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.sunrisekcdeveloper.medialion.android.app.ComposeKey
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data object RootKey : ComposeKey() {
    @Composable
    override fun ScreenComposable(modifier: Modifier) {
        RootScreen()
    }
}