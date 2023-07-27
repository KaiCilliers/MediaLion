package com.sunrisekcdeveloper.medialion.features.discovery

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.sunrisekcdeveloper.medialion.app.ComposeKey
import com.sunrisekcdeveloper.medialion.features.shared.MLMiniCollectionViewModel
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.servicesktx.add
import kotlinx.parcelize.Parcelize
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Immutable
@Parcelize
data object DiscoveryKey : ComposeKey(), KoinComponent {
    @Composable
    override fun ScreenComposable(modifier: Modifier) {
        DiscoveryScreen()
    }

    override fun bindServices(serviceBinder: ServiceBinder) {
        with(serviceBinder) {
            add(DiscoveryViewModel(
                get<MLDiscoveryViewModelNew>(),
                get<MLCategoriesViewModel>(),
                get<MLMiniCollectionViewModel>()
            ))
        }
    }
}