package com.example.medialion.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.medialion.SharedRes
import com.example.medialion.SharedTextResource
import com.zhuinden.simplestackextensions.fragmentsktx.backstack
import dev.icerock.moko.resources.desc.ResourceFormatted
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc

class DetailFragment : Fragment() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                var sliderValue by remember {
                    mutableStateOf(0f)
                }
                var s = SharedRes.strings.greeting.desc().toString(requireContext())
                var s2 = stringResource(id = SharedRes.strings.greeting.resourceId)
                val plural = pluralStringResource(id = SharedRes.plurals.dress.resourceId, count = 1)
                MediaLionTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column {
                            Slider(
                                value = sliderValue,
                                onValueChange = { sliderValue = it },
                                valueRange = 0f..10f
                            )
                            Text(
                                fontSize = 42.sp,
                                text = s + SharedTextResource().getMyPluralFormattedDesc(sliderValue.toInt())
                                    .toString(requireContext()),
                                modifier = Modifier.clickable {
                                    backstack.goBack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
