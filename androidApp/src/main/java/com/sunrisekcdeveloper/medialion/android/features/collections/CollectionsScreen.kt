package com.sunrisekcdeveloper.medialion.android.features.collections

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sunrisekcdeveloper.medialion.android.features.home.GlobalRouter
import com.sunrisekcdeveloper.medialion.android.features.search.SearchKey
import com.sunrisekcdeveloper.medialion.oldArch.MediaItemUI
import com.sunrisekcdeveloper.medialion.oldArch.domain.MediaType
import com.sunrisekcdeveloper.medialion.utils.rememberService
import com.zhuinden.simplestackcomposeintegration.core.LocalBackstack
import com.zhuinden.simplestackcomposeintegration.core.LocalComposeKey

@Composable
fun CollectionsScreen() {
    val currentBackStack = LocalBackstack.current
    val key = LocalComposeKey.current
    val globalRouter = rememberService<GlobalRouter>()

    Column(
        Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(fontWeight = FontWeight.Bold, text = "Collections")

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
                globalRouter.infoDialogRouter.show()
            },
            content = {
                Text("Show Dialog")
            },
        )
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                globalRouter.detailPreviewSheetRouter.show(MediaItemUI(
                    id = "vivamus",
                    title = "From Collections",
                    isFavorited = false,
                    posterUrl = "https://www.google.com/#q=constituam",
                    bannerUrl = "https://www.google.com/#q=justo",
                    genreIds = listOf(),
                    overview = "vituperatoribus",
                    popularity = 16.17,
                    voteAverage = 18.19,
                    voteCount = 3216,
                    releaseYear = "similique",
                    mediaType = MediaType.MOVIE
                ))
            },
            content = {
                Text("Open sheet")
            },
        )
    }
}