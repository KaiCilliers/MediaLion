package com.sunrisekcdeveloper.medialion.android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.sunrisekcdeveloper.medialion.android.theme.MediaLionTheme
import com.sunrisekcdeveloper.medialion.android.ui.search.DiscoveryViewModel
import com.sunrisekcdeveloper.medialion.android.ui.search.SearchKey
import com.zhuinden.simplestackextensions.fragmentsktx.backstack
import com.zhuinden.simplestackextensions.fragmentsktx.lookup

class HomeFragment : Fragment() {

    private val discoveryViewModel by lazy { lookup<DiscoveryViewModel>() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MediaLionTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {

                        val discoveryState by discoveryViewModel.state.collectAsState()

                        HomeScreen(
                            discoveryState = discoveryState,
                            submitDiscoveryAction = { discoveryViewModel.submitAction(it) },
                            onNavigateToSearchScreen = { backstack.goTo(SearchKey) }
                        )
                    }
                }
            }
        }
    }
}