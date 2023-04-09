package com.example.medialion.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.medialion.SharedRes
import com.example.medialion.SharedTextResource
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
                        modifier = Modifier.fillMaxSize()
                    ) {

                        val s1 = SharedTextResource().getUserName(null).toString(requireContext())
                        val s2 = SharedTextResource().getUserName("Nadine").toString(requireContext())

                        Text(
                            fontSize = 42.sp,
                            fontFamily = FontFamily(SharedRes.fonts.Quicksand.bold.getTypeface(requireContext())!!),
                            text = s1 + " / " + s2,
                            modifier = Modifier.clickable {
                                backstack.goTo(DetailScreen())
                            }
                        )
                    }
                }
            }
        }
    }
}
