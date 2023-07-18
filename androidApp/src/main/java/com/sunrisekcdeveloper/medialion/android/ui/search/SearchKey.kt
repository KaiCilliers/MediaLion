package com.sunrisekcdeveloper.medialion.android.ui.search

import androidx.fragment.app.Fragment
import com.sunrisekcdeveloper.medialion.android.app.BaseKey
import com.sunrisekcdeveloper.medialion.android.ui.discovery.DiscoveryViewModel
import com.sunrisekcdeveloper.medialion.features.search.MLSearchViewModelNew
import com.sunrisekcdeveloper.medialion.features.shared.MLMiniCollectionViewModel
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.servicesktx.add
import com.zhuinden.simplestackextensions.servicesktx.get
import kotlinx.parcelize.Parcelize
import org.koin.core.component.KoinComponent

@Parcelize
data object SearchKey : BaseKey(), KoinComponent {
    override fun instantiateFragment(): Fragment = SearchFragment()
    override fun bindServices(serviceBinder: ServiceBinder) {
        with(serviceBinder) {
            add(SearchViewModel(get<MLSearchViewModelNew>(), get<MLMiniCollectionViewModel>()))
        }
    }
}