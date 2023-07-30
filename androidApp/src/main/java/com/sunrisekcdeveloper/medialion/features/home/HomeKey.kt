package com.sunrisekcdeveloper.medialion.features.home

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.sunrisekcdeveloper.medialion.app.ComposeKey
import com.sunrisekcdeveloper.medialion.features.root.RootScreen
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.servicesktx.add
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Immutable
@Parcelize
data class HomeKey(
    val globalRouter: GlobalRouter
) : ComposeKey() {
    @Composable
    override fun ScreenComposable(modifier: Modifier) {
        HomeScreen()
    }

    override fun bindServices(serviceBinder: ServiceBinder) {
        with(serviceBinder) {
            add(globalRouter)
        }
    }
}

@Parcelize
data class GlobalRouter(
    val infoRouter: RootScreen.InfoRouter,
    val mediaPreviewRouter: RootScreen.MediaPreviewRouter,
    val quickCollectionRouter: RootScreen.QuickCollectionsRouter,
    val fullCollectionRouter: RootScreen.FullCollectionsRouter,
    val categoryDialogRouter: RootScreen.CategoriesDialogRouter,
) : Parcelable