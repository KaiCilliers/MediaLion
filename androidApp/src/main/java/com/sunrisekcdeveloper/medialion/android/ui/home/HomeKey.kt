package com.sunrisekcdeveloper.medialion.android.ui.home

import androidx.fragment.app.Fragment
import com.sunrisekcdeveloper.medialion.android.app.BaseKey
import com.sunrisekcdeveloper.medialion.android.ui.collections.CollectionViewModel
import com.sunrisekcdeveloper.medialion.android.ui.search.DiscoveryViewModel
import com.sunrisekcdeveloper.medialion.android.ui.search.SearchViewModel
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.servicesktx.add
import kotlinx.parcelize.Parcelize

@Parcelize
data object HomeKey : BaseKey() {
    override fun instantiateFragment(): Fragment = HomeFragment()
    override fun bindServices(serviceBinder: ServiceBinder) {
        with(serviceBinder) {
            add(SearchViewModel(backstack))
            add(DiscoveryViewModel(backstack))
            add(CollectionViewModel())
        }
    }
}