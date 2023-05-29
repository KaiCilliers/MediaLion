package com.sunrisekcdeveloper.medialion.android.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import com.sunrisekcdeveloper.medialion.android.theme.MediaLionTheme
import com.sunrisekcdeveloper.medialion.android.ui.collections.ui.CollectionScreen
import com.sunrisekcdeveloper.medialion.android.ui.components.ui.BottomBar
import com.sunrisekcdeveloper.medialion.android.ui.components.ui.BottomBarOption
import com.sunrisekcdeveloper.medialion.android.ui.discovery.DiscoveryScreen

@Composable
fun HomeScreen(
) {

    var selectedTab by remember {
        mutableStateOf(BottomBarOption.DISCOVERY)
    }

    ConstraintLayout {
        val (coreContent, bottombar) = createRefs()

        when (selectedTab) {
            BottomBarOption.DISCOVERY -> DiscoveryScreen(modifier = Modifier.constrainAs(coreContent) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(bottombar.top)
            })
            BottomBarOption.COLLECTION -> CollectionScreen(modifier = Modifier.constrainAs(coreContent) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(bottombar.top)
            })
        }

        BottomBar(
            selectedTab = selectedTab,
            onNewSelection = { selectedTab = it },
            modifier = Modifier.constrainAs(bottombar) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    MediaLionTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeScreen()
        }
    }
}