package com.example.medialion.android

import androidx.fragment.app.Fragment
import com.example.medialion.DiscoveryComponent
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.servicesktx.add
import kotlinx.parcelize.Parcelize

@Parcelize
data object DetailKey : BaseKey() {
    override fun instantiateFragment(): Fragment = DetailFragment()

    override fun bindServices(serviceBinder: ServiceBinder) {
        with(serviceBinder) {
            add(DiscoveryComponent())
        }
    }
}

