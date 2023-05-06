package com.example.medialion.android.ui.components.ui

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.medialion.android.R
import com.example.medialion.android.theme.MediaLionTheme
import com.example.medialion.android.ui.search.ui.MLTitledMediaGrid
import com.example.medialion.domain.models.MovieUiModel

@Composable
fun GridItemScreen(
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
                    .constrainAs(column) {
                        top.linkTo(
                            parent.top
                        )
                    }
            ){
                Row (
                    modifier = modifier
                        .background(MaterialTheme.colors.background)
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.back_arrow_icon),
                        contentDescription = "",
                        modifier = modifier
                            .size(30.dp)
                    )

                    Spacer(modifier = modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.about_icon),
                        contentDescription = "",
                        modifier = modifier
                            .size(30.dp)
                    )

                }
                MLTitledMediaGrid(
                    gridTitle = stringResource(id = com.example.medialion.R.string.top_results),
                    movies = (1..20).map { MovieUiModel(1, "HP", true) }.toList(),
                    suggestedMedia = (1..3).map {
                        "Suggested Heading #$it" to (1..20).map { MovieUiModel(1, "Movie #$it", true) }
                            .toList()
                    },
                    onMediaClicked ={}
                )

                    }

            BottomBar(
                modifier = modifier
                    .constrainAs(bottomBar) {
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
        }

}

@Preview
@Composable
private fun GridItemScreenPreview() {
    MediaLionTheme {
        GridItemScreen()
    }

}