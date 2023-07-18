package com.sunrisekcdeveloper.medialion.di

import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaRequirementsFactory
import org.koin.dsl.module

val factoryModule = module {
    factory<MediaRequirementsFactory> {
        MediaRequirementsFactory.D()
    }
}