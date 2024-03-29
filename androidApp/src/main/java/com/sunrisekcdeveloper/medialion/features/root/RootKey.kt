package com.sunrisekcdeveloper.medialion.features.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.sunrisekcdeveloper.medialion.app.ComposeKey
import com.sunrisekcdeveloper.medialion.components.collections.domain.InsertDefaultCollectionsUseCase
import com.sunrisekcdeveloper.medialion.features.discovery.DiscoveryViewModel
import com.sunrisekcdeveloper.medialion.features.discovery.MLCategoriesViewModel
import com.sunrisekcdeveloper.medialion.features.discovery.MLDiscoveryViewModelNew
import com.sunrisekcdeveloper.medialion.features.mycollection.MLMyCollectionViewModelNew
import com.sunrisekcdeveloper.medialion.features.shared.MLMiniCollectionViewModel
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.servicesktx.add
import kotlinx.parcelize.Parcelize
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Immutable
@Parcelize
data object RootKey : ComposeKey(), KoinComponent {
    @Composable
    override fun ScreenComposable(modifier: Modifier) {
        RootScreen()
    }

    override fun bindServices(serviceBinder: ServiceBinder) {
        with(serviceBinder) {
            add(
                com.sunrisekcdeveloper.medialion.features.collections.CollectionViewModel(
                    get<MLMyCollectionViewModelNew>(),
                    get<MLMiniCollectionViewModel>()
                )
            )

            // used only for categories state - remove dependency on DiscoveryViewModel in future
            add(
                DiscoveryViewModel(
                    get<MLDiscoveryViewModelNew>(),
                    get<MLCategoriesViewModel>(),
                    get<MLMiniCollectionViewModel>()
                )
            )

            add(get<InsertDefaultCollectionsUseCase>())
        }
    }
}