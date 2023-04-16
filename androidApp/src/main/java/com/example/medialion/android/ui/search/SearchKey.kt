package com.example.medialion.android.ui.search

import androidx.fragment.app.Fragment
import com.example.medialion.android.app.BaseKey
import com.example.medialion.data.searchComponent.MediaResponse
import com.example.medialion.domain.components.search.SearchMoviesUseCase
import com.example.medialion.domain.mappers.ListMapper
import com.example.medialion.domain.mappers.Mapper
import com.example.medialion.domain.models.Movie
import com.example.medialion.domain.models.MovieUiModel
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.servicesktx.add
import com.zhuinden.simplestackextensions.servicesktx.get
import com.zhuinden.simplestackextensions.servicesktx.lookup
import com.zhuinden.simplestackextensions.servicesktx.rebind
import kotlinx.coroutines.Dispatchers
import kotlinx.parcelize.Parcelize

@Parcelize
data object SearchKey : BaseKey() {
    override fun instantiateFragment(): Fragment = SearchFragment()
    override fun bindServices(serviceBinder: ServiceBinder) {
        with(serviceBinder) {
            add(Mapper.MovieDataMapper())
            rebind<Mapper<MediaResponse, Movie>>(get<Mapper.MovieDataMapper>())

            add(SearchMoviesUseCase.Default(lookup(), Dispatchers.IO, lookup()))
            rebind<SearchMoviesUseCase>(get<SearchMoviesUseCase.Default>())

            add(Mapper.MovieUiMapper())
            rebind<Mapper<Movie, MovieUiModel>>(get<Mapper.MovieUiMapper>())

            add(ListMapper.Impl<Movie, MovieUiModel>(lookup()))
            rebind<ListMapper<Movie, MovieUiModel>>(get<ListMapper.Impl<Movie, MovieUiModel>>())

            add(SearchViewModel(backstack))
        }
    }
}