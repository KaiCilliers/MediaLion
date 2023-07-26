package com.sunrisekcdeveloper.medialion.android.features.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.sunrisekcdeveloper.medialion.android.app.ComposeKey
import com.sunrisekcdeveloper.medialion.android.ui.collections.CollectionViewModel
import com.sunrisekcdeveloper.medialion.components.collections.domain.InsertDefaultCollectionsUseCase
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
            add(CollectionViewModel(get<MLMyCollectionViewModelNew>(), get<MLMiniCollectionViewModel>()))
            add(get<InsertDefaultCollectionsUseCase>())
        }
    }
}