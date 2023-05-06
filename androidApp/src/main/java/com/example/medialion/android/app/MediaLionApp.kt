package com.example.medialion.android.app

import android.app.Application
import android.content.Context
import com.example.medialion.clients.TMDBClient
import com.example.medialion.data.DefaultDispatcherProvider
import com.example.medialion.data.DispatcherProvider
import com.example.medialion.data.HttpClientFactory
import com.example.medialion.database.MediaLionDatabase
import com.example.medialion.local.DatabaseDriverFactory
import com.example.medialion.local.MediaLionDatabaseFactory
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