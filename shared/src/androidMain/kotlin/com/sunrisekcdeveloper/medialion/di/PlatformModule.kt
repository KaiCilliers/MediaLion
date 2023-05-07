package com.sunrisekcdeveloper.medialion.di

import android.content.Context
import com.sunrisekcdeveloper.medialion.clients.TMDBClient
import com.sunrisekcdeveloper.medialion.data.DefaultDispatcherProvider
import com.sunrisekcdeveloper.medialion.data.DispatcherProvider
import com.sunrisekcdeveloper.medialion.data.HttpClientFactory
import com.sunrisekcdeveloper.medialion.database.MediaLionDatabase
import com.sunrisekcdeveloper.medialion.local.DatabaseDriverFactory
import com.sunrisekcdeveloper.medialion.local.MediaLionDatabaseFactory
import org.koin.dsl.module

actual val platformModule = module {
    single<TMDBClient> { TMDBClient.Default(HttpClientFactory().create()) }
    single<MediaLionDatabase> { MediaLionDatabaseFactory(DatabaseDriverFactory(get<Context>())).create() }
    single<DispatcherProvider> { DefaultDispatcherProvider() }
}