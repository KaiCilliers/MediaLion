package com.sunrisekcdeveloper.medialion.android.features.collections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.sunrisekcdeveloper.medialion.android.app.ComposeKey
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data object CollectionsKey : ComposeKey() {
    @Composable
    override fun ScreenComposable(modifier: Modifier) {
        CollectionsScreen()
    }
}

