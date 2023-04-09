package com.example.medialion.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.medialion.android.theme.MediaLionTheme
import com.zhuinden.simplestackextensions.fragmentsktx.backstack

class SearchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MediaLionTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize().clickable {
                            backstack.goTo(DetailScreen())
                        }
                    ) {

                        Column {
                            Text(text = "heading 1", style = MaterialTheme.typography.h1)
                            Spacer(modifier = Modifier.height(10.dp))

                            Text(text = "heading 2", style = MaterialTheme.typography.h2)
                            Spacer(modifier = Modifier.height(10.dp))

                            Text(text = "heading 3", style = MaterialTheme.typography.h3)
                            Spacer(modifier = Modifier.height(10.dp))

                            Text(text = "subtitle 1", style = MaterialTheme.typography.subtitle1)
                            Spacer(modifier = Modifier.height(10.dp))

                            Text(text = "subtitle 2", style = MaterialTheme.typography.subtitle2)
                            Spacer(modifier = Modifier.height(10.dp))

                            Text(text = "body", style = MaterialTheme.typography.body1)
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            }
        }
    }
}
