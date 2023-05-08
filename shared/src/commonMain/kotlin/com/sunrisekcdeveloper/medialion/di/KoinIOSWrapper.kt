package com.sunrisekcdeveloper.medialion.di

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
        coroutineScope = null,
    )
}