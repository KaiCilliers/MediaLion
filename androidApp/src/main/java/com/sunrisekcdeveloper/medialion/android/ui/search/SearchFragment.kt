package com.sunrisekcdeveloper.medialion.android.ui.search

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
import com.zhuinden.simplestackextensions.fragmentsktx.lookup
import com.zhuinden.simplestackextensions.navigatorktx.backstack

class SearchFragment : Fragment() {

    private val viewModel by lazy { lookup<SearchViewModel>() }
    private val viewModelDiscovery by lazy { lookup<DiscoveryViewModel>() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MediaLionTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        val state by viewModel.state.collectAsState()
                        val discState by viewModelDiscovery.state.collectAsState()
                        val collectionState by viewModel.collectionState.collectAsState()
                        val genreSta by viewModelDiscovery.genres.genres.collectAsState()

                        println("deadpool - got state $discState")
                        println("deadpool - got genres $genreSta")

                        SearchScreen(
                            state = state,
                            collectionState = collectionState,
                            submitAction = { viewModel.submitAction(it) },
                            backstack = backstack,
                        )
                    }
                }
            }
        }
    }
}
