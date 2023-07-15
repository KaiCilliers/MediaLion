package com.sunrisekcdeveloper.medialion.oldArch.di

import com.sunrisekcdeveloper.medialion.oldArch.clients.TMDBClient
import com.sunrisekcdeveloper.medialion.oldArch.data.DefaultDispatcherProvider
import com.sunrisekcdeveloper.medialion.oldArch.data.DispatcherProvider
import com.sunrisekcdeveloper.medialion.oldArch.data.HttpClientFactory
import com.sunrisekcdeveloper.medialion.database.MediaLionDatabase
import com.sunrisekcdeveloper.medialion.oldArch.local.DatabaseDriverFactory
import com.sunrisekcdeveloper.medialion.oldArch.local.MediaLionDatabaseFactory
import org.koin.dsl.module

actual val platformModule = module {
    single<TMDBClient> {
        TMDBClient.Default(HttpClientFactory().create())
    }
    single<MediaLionDatabase> {
        MediaLionDatabaseFactory(DatabaseDriverFactory()).create()
    }
    single<DispatcherProvider> {
        DefaultDispatcherProvider()
    }
}