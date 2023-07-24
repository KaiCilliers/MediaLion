package com.sunrisekcdeveloper.medialion.android.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import com.sunrisekcdeveloper.medialion.android.features.root.RootKey
import com.sunrisekcdeveloper.medialion.android.theme.MediaLionTheme
import com.zhuinden.simplestack.History
import com.zhuinden.simplestackcomposeintegration.core.ComposeNavigator
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider

class NewAct : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as com.sunrisekcdeveloper.medialion.android.app.MediaLionApp

        setContent {
            MediaLionTheme {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    ComposeNavigator {
                        createBackstack(
                            scopedServices = DefaultServiceProvider(),
                            globalServices = app.globalServices,
                            initialKeys = History.of(RootKey)
                        )
                    }
                }
            }
        }
    }
}