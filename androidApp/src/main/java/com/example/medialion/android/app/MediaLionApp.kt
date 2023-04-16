package com.example.medialion.android.app

import android.app.Application
import com.example.medialion.data.HttpClientFactory
import com.example.medialion.data.searchComponent.TMDBClient
import com.zhuinden.simplestack.GlobalServices
import com.zhuinden.simplestackextensions.servicesktx.add
import com.zhuinden.simplestackextensions.servicesktx.rebind

class MediaLionApp : Application() {
    lateinit var globalServices: GlobalServices
        private set

    override fun onCreate() {
        super.onCreate()

        val tmdbClient = TMDBClient.Default(HttpClientFactory().create())

        globalServices = GlobalServices.builder()
            .add(tmdbClient)
            .rebind<TMDBClient>(tmdbClient)
            .build()
    }
}