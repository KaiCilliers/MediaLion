package com.sunrisekcdeveloper.medialion.android.features.root

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sunrisekcdeveloper.medialion.android.features.home.GlobalRouter
import com.sunrisekcdeveloper.medialion.android.features.home.HomeKey
import com.sunrisekcdeveloper.medialion.android.features.home.components.CustomDialog
import com.sunrisekcdeveloper.medialion.android.features.shared.DetailPreviewScreen
import com.sunrisekcdeveloper.medialion.android.ui.collections.CollectionViewModel
import com.sunrisekcdeveloper.medialion.android.ui.saveToCollection.ui.SaveToCollectionScreen
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.SingleMediaItem
import com.sunrisekcdeveloper.medialion.features.shared.InsertCollection
import com.sunrisekcdeveloper.medialion.features.shared.UpdateCollection
import com.sunrisekcdeveloper.medialion.oldArch.MediaItemUI
import com.sunrisekcdeveloper.medialion.utils.rememberService
import com.zhuinden.simplestack.History
import com.zhuinden.simplestackcomposeintegration.core.ComposeNavigator
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class)
class RootScreen private constructor() {
    interface InfoRouter {
        fun show()
        fun showWithResult(onResult: (String) -> Unit)
    }

    interface MediaPreviewRouter {
        fun show(media: MediaItemUI)
    }

    interface QuickCollectionsRouter {
        fun show(media: SingleMediaItem)
    }

    sealed interface MediaContent : Parcelable {
        @Parcelize
        data object NoContent : MediaContent

        @Parcelize
        data class Content(val id: String = UUID.randomUUID().toString(), val media: @RawValue MediaItemUI) : Parcelable, MediaContent
    }

    companion object {
        @Composable
        @SuppressLint("ComposableNaming")
        operator fun invoke() {

            val collectionsViewModel = rememberService<CollectionViewModel>()
            val collectionDialogState by collectionsViewModel.collectionDialogState.collectAsState()

            val scope = rememberCoroutineScope()
            var showInfoDialog by rememberSaveable { mutableStateOf(false) }
            var showMiniCollectionsDialog by rememberSaveable { mutableStateOf(false) }
            var showMediaDetailSheet: MediaContent by rememberSaveable { mutableStateOf(MediaContent.NoContent) }
            var quickCollectionDialog: MediaContent by rememberSaveable { mutableStateOf(MediaContent.NoContent) }
            var onDialogResult: Foo by rememberSaveable { mutableStateOf(Foo()) }

            val mediaPreviewSheet = rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden,
                confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
                skipHalfExpanded = true,
                animationSpec = spring(1.4f)
            )

            val infoRouter = object : InfoRouter {
                override fun show() {
                    println("deadpool - showing dialog ($showInfoDialog) to true")
                    showInfoDialog = true
                    println("deadpool - new dialog value is ($showInfoDialog)")
                }

                override fun showWithResult(onResult: (String) -> Unit) {
                    onDialogResult = Foo(onResult)
                    showInfoDialog = true
                }
            }
            val mediaPreviewRouter = object : MediaPreviewRouter {
                override fun show(media: MediaItemUI) {
                    showMediaDetailSheet = MediaContent.Content(media = media)
                }
            }

            val miniCollectionRouter = object : QuickCollectionsRouter {
                override fun show(media: SingleMediaItem) {
                    quickCollectionDialog = MediaContent.Content(media = MediaItemUI.from(media))
                    showMiniCollectionsDialog = true
                }
            }

            LaunchedEffect(showMediaDetailSheet) {
                scope.launch {
                    when (showMediaDetailSheet) {
                        is MediaContent.Content -> mediaPreviewSheet.show()
                        MediaContent.NoContent -> mediaPreviewSheet.hide()
                    }
                }
            }
            LaunchedEffect(mediaPreviewSheet) {
                snapshotFlow { mediaPreviewSheet.isVisible }.collect { isVisible ->
                    println("is visiblie $isVisible")
                    if (!isVisible) {
                        showMediaDetailSheet = MediaContent.NoContent
                    }
                }
            }

            if (showInfoDialog) {
                CustomDialog(
                    onDismiss = {
                        showInfoDialog = false
                    },
                    onResult = {
                        println("got result $it")
                        onDialogResult.s.invoke(it)
                        onDialogResult = Foo({})
                    }
                )
            }

            if (showMiniCollectionsDialog) {
                when (val currentState = quickCollectionDialog) {
                    is MediaContent.Content -> {
                        SaveToCollectionScreen(
                            onDismiss = { showMiniCollectionsDialog = false },
                            miniCollectionUIState = collectionDialogState,
                            onCreateCollection = { collectionName -> collectionsViewModel.submit(InsertCollection(collectionName)) },
                            onUpdateCollection = { newCollection -> collectionsViewModel.submit(UpdateCollection(newCollection)) },
                            targetedMediaItem = currentState.media.toDomain()
                        )
                    }
                    MediaContent.NoContent -> {}
                }
            }

            val globalRouter = GlobalRouter(
                infoRouter = infoRouter,
                mediaPreviewRouter = mediaPreviewRouter,
                quickCollectionRouter = miniCollectionRouter,
            )

            ModalBottomSheetLayout(
                sheetState = mediaPreviewSheet,
                sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                sheetContent = {
                    when (val currentMediaToShow = showMediaDetailSheet) {
                        is MediaContent.Content -> {
                            DetailPreviewScreen(
                                mediaItem = currentMediaToShow.media,
                                onCloseClick = { scope.launch { mediaPreviewSheet.hide() } },
                                onMyListClick = { miniCollectionRouter.show(it) },
                            )
                        }

                        MediaContent.NoContent -> {}
                    }
                }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    ComposeNavigator(id = "root") {
                        createBackstack(
                            History.of(HomeKey(globalRouter)),
                            scopedServices = DefaultServiceProvider()
                        )
                    }
                }
            }
        }
    }
}
