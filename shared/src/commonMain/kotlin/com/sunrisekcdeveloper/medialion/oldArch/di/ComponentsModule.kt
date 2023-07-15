package com.sunrisekcdeveloper.medialion.oldArch.di

import com.sunrisekcdeveloper.medialion.oldArch.domain.search.CollectionComponent
import com.sunrisekcdeveloper.medialion.oldArch.domain.search.SearchComponent
import org.koin.dsl.module

val componentsModule = module {
    factory<SearchComponent> { SearchComponent.Default(get(), get(), get(), get(), get(), get(), get()) }
    factory<CollectionComponent> { CollectionComponent.Default(get(), get(), get(), get(), get(), get(), get(), get(), get()) }
}