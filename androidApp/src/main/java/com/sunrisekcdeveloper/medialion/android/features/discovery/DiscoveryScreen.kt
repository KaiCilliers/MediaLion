package com.sunrisekcdeveloper.medialion.android.features.discovery

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sunrisekcdeveloper.medialion.android.features.home.GlobalRouter
import com.sunrisekcdeveloper.medialion.android.features.search.SearchKey
import com.sunrisekcdeveloper.medialion.oldArch.MediaItemUI
import com.sunrisekcdeveloper.medialion.oldArch.domain.MediaType
import com.sunrisekcdeveloper.medialion.utils.rememberService
import com.sunrisekcdeveloper.medialion.utils.showToast
import com.zhuinden.simplestackcomposeintegration.core.LocalBackstack
import com.zhuinden.simplestackcomposeintegration.core.LocalComposeKey

@Composable
fun DiscoveryScreen() {
    val currentBackStack = LocalBackstack.current
    val key = LocalComposeKey.current
    val context = LocalContext.current
    val globalRouter = rememberService<GlobalRouter>()

    Column(
        Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(fontWeight = FontWeight.Bold, text = "Discovery")

            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                currentBackStack.parentServices?.goTo(SearchKey(globalRouter))
            },
            content = {
                Text("Go to Search")
            },
        )
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                println("deadpool - $key")
                globalRouter.infoRouter.show()
            },
            content = {
                Text("Open Info Dialog")
            },
        )
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                println("deadpool - $key")

                    globalRouter.infoRouter.showWithResult {
                        println("got result $it")
                        context.showToast("got result $it")
                    }
            },
            content = {
                Text("Open Info Dialog for result")
            },
        )
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                println("deadpool - $key")
                globalRouter.mediaPreviewRouter.show(MediaItemUI(
                    id = "tristique",
                    title = "From Discovery",
                    isFavorited = false,
                    posterUrl = "http://www.bing.com/search?q=semper",
                    bannerUrl = "https://duckduckgo.com/?q=partiendo",
                    genreIds = listOf(),
                    overview = "iudicabit",
                    popularity = 4.5,
                    voteAverage = 6.7,
                    voteCount = 9768,
                    releaseYear = "no",
                    mediaType = MediaType.MOVIE,
                ))
            },
            content = {
                Text("Open sheet")
            },
        )
    }
}