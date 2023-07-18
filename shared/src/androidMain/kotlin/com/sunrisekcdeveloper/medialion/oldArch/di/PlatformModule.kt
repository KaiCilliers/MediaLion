package com.sunrisekcdeveloper.medialion.oldArch.di

import android.content.Context
import com.sunrisekcdeveloper.medialion.components.shared.TMDBClientNew
import com.sunrisekcdeveloper.medialion.database.MediaLionDatabase
import com.sunrisekcdeveloper.medialion.oldArch.clients.TMDBClient
import com.sunrisekcdeveloper.medialion.oldArch.data.DefaultDispatcherProvider
import com.sunrisekcdeveloper.medialion.oldArch.data.DispatcherProvider
import com.sunrisekcdeveloper.medialion.oldArch.data.HttpClientFactory
import com.sunrisekcdeveloper.medialion.oldArch.local.DatabaseDriverFactory
import com.sunrisekcdeveloper.medialion.oldArch.local.MediaLionDatabaseFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual val platformModule = module {
    single<TMDBClientNew> {
        TMDBClientNew(
            httpClient = HttpClientFactory().create(),
            mapper = get(named(MapperNames.SingleMediaItemNames.responseToSingleMediaApiDto))
        )
    }

    single<TMDBClient> { TMDBClient.Default(HttpClientFactory().create()) }
    single<MediaLionDatabase> { MediaLionDatabaseFactory(DatabaseDriverFactory(get<Context>())).create() }
    single<DispatcherProvider> { DefaultDispatcherProvider() }
}