package com.sunrisekcdeveloper.medialion.di

import com.sunrisekcdeveloper.medialion.domain.collection.MLCollectionViewModel
import com.sunrisekcdeveloper.medialion.domain.discovery.MLDiscoveryViewModel
import com.sunrisekcdeveloper.medialion.domain.search.MLSearchViewModel
import com.sunrisekcdeveloper.medialion.mappers.ListMapper
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named

fun initKoin() {
    startKoin {
        modules(sharedModule)
    }
}

class WrapperMLSearchViewModel : KoinComponent {
    fun instance() = MLSearchViewModel(
        searchComponent = get(),
        collectionComponent = get(),
        mediaItemMapper = get(named(MapperNames.mediaDomainToUI)),
        movieListMapper = ListMapper.Impl(get(named(MapperNames.movieDomainToUI))),
        tvListMapper = ListMapper.Impl(get(named(MapperNames.tvDomainToUI))),
        coroutineScope = null,
    )
}

class WrapperMLCollectionViewModel : KoinComponent {
    fun instance() = MLCollectionViewModel(
        collectionComponent = get(),
        searchComponent = get(),
        mediaListMapper = ListMapper.Impl(get(named(MapperNames.mediaDomainToUI))),
        coroutineScope = null
    )
}

class WrapperMLDiscoveryViewModel : KoinComponent {
    fun instance() = MLDiscoveryViewModel(
        fetchDiscoveryContent = get(),
        coroutineScope = null
    )
}