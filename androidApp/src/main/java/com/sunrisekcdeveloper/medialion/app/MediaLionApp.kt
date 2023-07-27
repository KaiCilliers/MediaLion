package com.sunrisekcdeveloper.medialion.app

import android.app.Application
import android.content.Context
import com.sunrisekcdeveloper.medialion.di.sharedModule
import com.zhuinden.simplestack.GlobalServices
import com.zhuinden.simplestackextensions.servicesktx.add
import com.zhuinden.simplestackextensions.servicesktx.rebind
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MediaLionApp : Application() {
    lateinit var globalServices: GlobalServices
        private set

    override fun onCreate() {
        super.onCreate()

        Napier.base(DebugAntilog())

        globalServices = GlobalServices.builder()
            .add(this)
            .rebind<Context>(this)
            .build()

        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@MediaLionApp)
            // Load modules
            modules(sharedModule)
        }
    }
}