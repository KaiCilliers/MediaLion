package com.sunrisekcdeveloper.medialion.di

import com.sunrisekcdeveloper.medialion.components.shared.TMDBClientNew
import com.sunrisekcdeveloper.medialion.utils.DefaultDispatcherProvider
import com.sunrisekcdeveloper.medialion.utils.DispatcherProvider
import com.sunrisekcdeveloper.medialion.network.HttpClientFactory
import com.sunrisekcdeveloper.medialion.database.MediaLionDatabase
import com.sunrisekcdeveloper.medialion.storage.DatabaseDriverFactory
import com.sunrisekcdeveloper.medialion.storage.MediaLionDatabaseFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual val platformModule = module {
    single<TMDBClientNew> {
        TMDBClientNew(
            httpClient = HttpClientFactory().create(),
            mapper = get(named(MapperNames.SingleMediaItemNames.responseToSingleMediaApiDto))
        )
    }
    single<MediaLionDatabase> {
        MediaLionDatabaseFactory(DatabaseDriverFactory()).create()
    }
    single<DispatcherProvider> {
        DefaultDispatcherProvider()
    }
}