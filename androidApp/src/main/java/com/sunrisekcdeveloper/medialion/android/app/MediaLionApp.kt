package com.sunrisekcdeveloper.medialion.android.app

import android.app.Application
import android.content.Context
import com.sunrisekcdeveloper.medialion.clients.TMDBClient
import com.sunrisekcdeveloper.medialion.data.DefaultDispatcherProvider
import com.sunrisekcdeveloper.medialion.data.DispatcherProvider
import com.sunrisekcdeveloper.medialion.data.HttpClientFactory
import com.sunrisekcdeveloper.medialion.database.MediaLionDatabase
import com.sunrisekcdeveloper.medialion.local.DatabaseDriverFactory
import com.sunrisekcdeveloper.medialion.local.MediaLionDatabaseFactory
import com.zhuinden.simplestack.GlobalServices
import com.zhuinden.simplestackextensions.servicesktx.add
import com.zhuinden.simplestackextensions.servicesktx.rebind

class MediaLionApp : Application() {
    lateinit var globalServices: GlobalServices
        private set

    override fun onCreate() {
        super.onCreate()

        val tmdbClient = TMDBClient.Default(HttpClientFactory().create())
        val database = MediaLionDatabaseFactory(DatabaseDriverFactory(this)).create()
        val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()

        globalServices = GlobalServices.builder()
            .add(this)
            .rebind<Context>(this)

            .add(tmdbClient)
            .rebind<TMDBClient>(tmdbClient)

            .add(database)
            .rebind<MediaLionDatabase>(database)

            .add(dispatcherProvider)
            .rebind<DispatcherProvider>(dispatcherProvider)

            .build()
    }
}