package com.example.medialion.android.ui.discovery.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.medialion.android.R
import com.example.medialion.android.theme.MediaLionTheme
import com.example.medialion.android.ui.components.ui.BottomBar
import com.example.medialion.android.ui.search.ui.MLTitledMediaRow
import com.example.medialion.domain.models.MovieUiModel

@Composable
fun DiscoveryScreen(
    modifier: Modifier = Modifier
) {
    ConstraintLayout (
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ){

        val (containerTop, column, bottomBar) = createRefs()

        ConstraintLayout(
            modifier = Modifier.constrainAs(containerTop) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        ) {
            Column (
                modifier = modifier
                    .background(MaterialTheme.colors.background)
                    .padding(horizontal = 16.dp)
                    .constrainAs(column) {
                        top.linkTo(parent.top)
                        width = Dimension.fillToConstraints
                    }

            ){
                Row (
                    modifier = modifier
                        .background(MaterialTheme.colors.background)
                        .padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(
                        painter = painterResource(id = R.drawable.back_arrow_icon),
                        contentDescription = "",
                        modifier = modifier
                            .size(30.dp)

                    )
                    Spacer(modifier = modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.logo_icon),
                        contentDescription = "",
                        modifier = modifier
                            .size(60.dp)

                    )
                    Spacer(modifier = modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.about_icon),
                        contentDescription = "",
                        modifier = modifier
                            .size(30.dp)

                    )
                }
                FilterCategories()



                MLTitledMediaRow(rowTitle = "Horror", movies = listOf(
                    MovieUiModel(1, "HP", true),
                    MovieUiModel(1, "HP", true),
                    MovieUiModel(1, "HP", true),
                    MovieUiModel(1, "HP", true),
                    MovieUiModel(1, "HP", true),
                ), onMediaItemClicked = {}
                )



            }
            BottomBar(
                modifier = modifier
                    .constrainAs(bottomBar) {
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    }
            )
    }

    }




    }


@Preview
@Composable
private fun DiscoveryScreenPreview() {
    MediaLionTheme {
        DiscoveryScreen()
    }

}