package com.sunrisekcdeveloper.medialion.android.app

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import com.sunrisekcdeveloper.medialion.android.R
import com.sunrisekcdeveloper.medialion.android.ui.home.HomeKey
import com.sunrisekcdeveloper.medialion.android.ui.search.SearchKey
import com.zhuinden.simplestack.AheadOfTimeWillHandleBackChangedListener
import com.zhuinden.simplestack.BackHandlingModel
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.SimpleStateChanger
import com.zhuinden.simplestack.StateChange
import com.zhuinden.simplestack.navigator.Navigator
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentStateChanger
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider

class MainActivity : FragmentActivity(), SimpleStateChanger.NavigationHandler {
    private lateinit var fragmentStateChanger: DefaultFragmentStateChanger
    private lateinit var backstack: Backstack
    private val backPressedCallback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            backstack.goBack()
        }
    }
    private val updateBackPressedCallback = AheadOfTimeWillHandleBackChangedListener {
        backPressedCallback.isEnabled = it
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onBackPressedDispatcher.addCallback(backPressedCallback)

        fragmentStateChanger = DefaultFragmentStateChanger(supportFragmentManager,
            R.id.container_fragment
        )

        val app = application as com.sunrisekcdeveloper.medialion.android.app.MediaLionApp
        val globalServices = app.globalServices

        backstack = Navigator.configure()
            .setBackHandlingModel(BackHandlingModel.AHEAD_OF_TIME)
            .setStateChanger(SimpleStateChanger(this))
            .setScopedServices(DefaultServiceProvider())
            .setGlobalServices(globalServices)
            .install(this, findViewById(R.id.container_fragment), History.single(HomeKey))

        backPressedCallback.isEnabled = backstack.willHandleAheadOfTimeBack()
        backstack.addAheadOfTimeWillHandleBackChangedListener(updateBackPressedCallback)
    }

    override fun onDestroy() {
        backstack.removeAheadOfTimeWillHandleBackChangedListener(updateBackPressedCallback)
        super.onDestroy()
    }

    override fun onNavigationEvent(stateChange: StateChange) {
        fragmentStateChanger.handleStateChange(stateChange)
    }
}