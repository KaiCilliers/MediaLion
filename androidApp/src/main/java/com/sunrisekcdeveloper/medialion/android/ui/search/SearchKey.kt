package com.sunrisekcdeveloper.medialion.android.ui.search

import androidx.fragment.app.Fragment
import com.sunrisekcdeveloper.medialion.android.app.BaseKey
import com.sunrisekcdeveloper.medialion.mappers.ListMapper
import com.sunrisekcdeveloper.medialion.mappers.Mapper
import com.sunrisekcdeveloper.medialion.repos.CollectionRepository
import com.sunrisekcdeveloper.medialion.repos.MovieRepository
import com.sunrisekcdeveloper.medialion.repos.TVRepository
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.servicesktx.add
import com.zhuinden.simplestackextensions.servicesktx.lookup
import com.zhuinden.simplestackextensions.servicesktx.rebind
import kotlinx.parcelize.Parcelize

@Parcelize
data object SearchKey : com.sunrisekcdeveloper.medialion.android.app.BaseKey() {
    override fun instantiateFragment(): Fragment = SearchFragment()
    override fun bindServices(serviceBinder: ServiceBinder) {
        with(serviceBinder) {

            val movieRepository = MovieRepository.Default(
                database = lookup(),
                client = lookup(),
                dispatcherProvider = lookup(),
                responseToDomain = Mapper.MovieEntity.ResponseToDomain(),
                domainToCache = Mapper.MovieEntity.DomainToCache(),
                cacheToDomain = Mapper.MovieEntity.CacheToDomain(),
                responseToDomainListMapper = ListMapper.Impl(Mapper.MovieEntity.ResponseToDomain())
            )

            val tvRepository = TVRepository.Default(
                database = lookup(),
                client = lookup(),
                dispatcherProvider = lookup(),
                responseToDomain = Mapper.TVShowEntity.ResponseToDomain(),
                domainToCache = Mapper.TVShowEntity.DomainToCache(),
                cacheToDomain = Mapper.TVShowEntity.CacheToDomain(),
                responseToDomainListMapper = ListMapper.Impl(Mapper.TVShowEntity.ResponseToDomain()),
            )

            val collectionRepository = CollectionRepository.Default(
                database = lookup(),
                dispatcherProvider = lookup(),
                movieCacheToDomainList = ListMapper.Impl(Mapper.MovieEntity.CacheToDomain()),
                tvShowCacheToDomainList = ListMapper.Impl(Mapper.TVShowEntity.CacheToDomain()),
                movieDomainToMediaDomain = ListMapper.Impl(Mapper.MovieEntity.DomainToMediaDomain()),
                tvShowDomainToMediaDomain = ListMapper.Impl(Mapper.TVShowEntity.DomainToMediaDomain()),
            )

            add(movieRepository)
            rebind<MovieRepository>(movieRepository)

            add(tvRepository)
            rebind<TVRepository>(tvRepository)

            add(collectionRepository)
            rebind<CollectionRepository>(collectionRepository)

            add(SearchViewModel(backstack))
        }
    }
}