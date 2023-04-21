package com.example.medialion.android.ui.detailPreview.ui

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.medialion.ColorRes
import com.example.medialion.R
import com.example.medialion.android.theme.MediaLionTheme
import com.example.medialion.android.ui.extensions.gradientBackground
import com.example.medialion.android.ui.search.ui.MLMediaPoster
import com.example.medialion.domain.components.detailPreview.DetailPreviewState
import com.example.medialion.domain.models.MovieUiModel
import com.example.medialion.domain.models.SimpleMediaItem
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailPreviewScreen(modifier: Modifier = Modifier) {
   val sheetState = rememberBottomSheetState(
       initialValue = BottomSheetValue.Collapsed,
       animationSpec = spring(
           dampingRatio = Spring.DampingRatioMediumBouncy
       )
   )
    val scaffholdState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
        modifier = modifier.height(500.dp),
        scaffoldState = scaffholdState,
        sheetContent = { BottomSheetNew()},
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(12.dp),


    )
    {
        Box (
            modifier = modifier
                .fillMaxWidth()
                .height(300.dp),
            contentAlignment = Alignment.Center
        ){
            Button(onClick = {
                scope.launch {
                    sheetState.expand()
                }
            }) {
                Text(
                    text = "Toggle sheet",
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun BottomSheetNew(modifier: Modifier = Modifier) {
    Surface (color = Color.Black){
        ConstraintLayout (
            modifier = modifier.fillMaxSize(),
        ){
            val (
                poster, name, description, date, addToListButton, listText, closeImage
            ) = createRefs()

            val rightGuideLine = createGuidelineFromStart(0.4f)

            MLMediaPoster(
                mediaItem = SimpleMediaItem(
                    id = "",
                    title = "HP",
                    posterUrl = ""
                ),
                modifier = modifier
                    .constrainAs(poster) {
                        start.linkTo(parent.start, 16.dp)
                        top.linkTo(parent.top, 16.dp)
                        end.linkTo(rightGuideLine, 16.dp)
                        width = Dimension.fillToConstraints
                    }
            )



            Text(
                text = "It is a story about Harry Potter, an orphan brought up by his aunt and " +
                    "uncle because his parents were killed when he was a baby. Harry is unloved by" +
                    " his uncle and aunt but everything changes when he is invited to join Hogwarts" +
                    " School of Witchcraft and Wizardry and he finds out he's a wizard.",
                color = Color.White,
                style = MaterialTheme.typography.subtitle2,
                modifier = modifier
                    .constrainAs(description) {
                        top.linkTo(parent.top, 50.dp)
                        start.linkTo(poster.end, 20.dp)
                        end.linkTo(parent.end, 50.dp)
                    }
                )

        }
    }
}

@Composable
fun BottomSheet(modifier: Modifier = Modifier){
Box (
    modifier = Modifier
        .fillMaxWidth()
        .gradientBackground(
            colors = listOf(
                colorResource(id = ColorRes.primary.resourceId),
                colorResource(id = ColorRes.primaryVariantBlue.resourceId)
            ),
            -90f

        ),
)
        {
    Row (
        modifier = modifier
            .padding(top = 20.dp, start = 16.dp, end = 16.dp),
    ){
      MLMediaPoster(
          mediaItem = SimpleMediaItem(
              id = "",
              title = "HP",
              posterUrl = ""
          ),
          modifier = modifier
              .size(height = 170.dp, width = 120.dp)

      )

        Column (
            modifier = modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ){
            Row {
                Text(
                    text = "Harry Potter",
                    color = Color.White,
                    style = MaterialTheme.typography.h2,
                    overflow = TextOverflow.Visible
                )
                Spacer(modifier = modifier.width(15.dp))
                Image(
                    painter = painterResource(id = com.example.medialion.android.R.drawable.close_icon),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = modifier
                        .size(30.dp)
                    )
            }

            Text(
                text = "2001",
                color = Color.White,
                style = MaterialTheme.typography.subtitle2
            )
            Text(
                text = "It is a story about Harry Potter, an orphan brought up by his aunt and uncle " +
                        "because his parents were killed when he was a baby. Harry is unloved by his " +
                        "uncle and aunt but everything changes when he is invited to join Hogwarts " +
                        "School of Witchcraft and Wizardry and he finds out he's a wizard.",
                color = Color.White,
                style = MaterialTheme.typography.subtitle2,

            )
            Spacer(modifier = modifier.height(16.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = com.example.medialion.android.R.drawable.add_to_list_icon),
                    contentDescription = "",
                    modifier = modifier.size(90.dp)
                )
                Spacer(modifier = modifier.height(10.dp))
                Text(
                    text = "My List",
                    color = Color.White,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center

                )
            }
        }

    }
}
}


@Preview
@Composable
fun DetailPreviewScreenPreview() {
    MediaLionTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
          BottomSheetNew()
        }
    }
}