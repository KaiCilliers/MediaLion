package com.example.medialion.android.ui.search

import androidx.fragment.app.Fragment
import com.example.medialion.android.app.BaseKey
import com.example.medialion.data.models.MovieListResponse
import com.example.medialion.domain.mappers.Mapper
import com.example.medialion.domain.models.OldMovie
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.servicesktx.add
import com.zhuinden.simplestackextensions.servicesktx.get
import com.zhuinden.simplestackextensions.servicesktx.rebind
import kotlinx.parcelize.Parcelize

@Parcelize
data object SearchKey : BaseKey() {
    override fun instantiateFragment(): Fragment = SearchFragment()
    override fun bindServices(serviceBinder: ServiceBinder) {
        with(serviceBinder) {
            add(Mapper.MovieDataMapper())
            rebind<Mapper<MovieListResponse, OldMovie>>(get<Mapper.MovieDataMapper>())
            add(SearchViewModel(backstack))
        }
    }
}