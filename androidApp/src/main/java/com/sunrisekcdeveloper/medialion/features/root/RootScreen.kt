package com.sunrisekcdeveloper.medialion.features.root

import android.annotation.SuppressLint
import android.os.Parcel
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaCategory
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.SingleMediaItem
import com.sunrisekcdeveloper.medialion.features.discovery.DiscoveryViewModel
import com.sunrisekcdeveloper.medialion.features.discovery.components.CategoriesDialog
import com.sunrisekcdeveloper.medialion.features.home.GlobalRouter
import com.sunrisekcdeveloper.medialion.features.home.HomeKey
import com.sunrisekcdeveloper.medialion.features.home.components.MLAppInfoDialog
import com.sunrisekcdeveloper.medialion.features.mycollection.MyCollectionsContent
import com.sunrisekcdeveloper.medialion.features.root.components.MLCollectionDetail
import com.sunrisekcdeveloper.medialion.features.shared.InsertCollection
import com.sunrisekcdeveloper.medialion.features.shared.MLDetailPreviewSheet
import com.sunrisekcdeveloper.medialion.features.shared.MLQuickCollectionsDialog
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
    interface InfoRouter : Parcelable {
        fun show()
    }

    interface MediaPreviewRouter : Parcelable {
        fun show(media: MediaItemUI)
    }

    interface QuickCollectionsRouter : Parcelable {
        fun show(media: SingleMediaItem)
    }

    interface FullCollectionsRouter : Parcelable {
        fun show(collection: CollectionNew)
    }

    interface CategoriesDialogRouter : Parcelable {
        fun showWithResult(onResult: (MediaCategory) -> Unit)
    }

    sealed interface MediaContent{
        data object NoContent : MediaContent
        data class PreviewMedia(val id: String = UUID.randomUUID().toString(), val media: MediaItemUI) : MediaContent
    }

    sealed interface CollectionContent{
        data object NoContent : CollectionContent
        data class Content(val id: String = UUID.randomUUID().toString(), val collection: CollectionNew) : CollectionContent
    }

    companion object {
        @Composable
        @SuppressLint("ComposableNaming")
        operator fun invoke() {

            val collectionsViewModel = rememberService<com.sunrisekcdeveloper.medialion.features.collections.CollectionViewModel>()
            // TODO to be removed in future - used only to access media categories
            val discoveryViewModel = rememberService<DiscoveryViewModel>()

            val collectionDialogState by collectionsViewModel.collectionDialogState.collectAsState()
            val collectionsState by collectionsViewModel.collectionsState.collectAsState()
            val mediaCategoriesState by discoveryViewModel.categoriesState.collectAsState()

            val scope = rememberCoroutineScope()

            var showInfoDialog by rememberSaveable { mutableStateOf(false) }

            var showMiniCollectionsDialog by rememberSaveable { mutableStateOf(false) }

            var showMediaDetailSheet: MediaContent by remember { mutableStateOf(MediaContent.NoContent) }

            var quickCollectionDialog: MediaContent by remember { mutableStateOf(MediaContent.NoContent) }

            var showFullCollectionsSheet: CollectionContent by remember { mutableStateOf(CollectionContent.NoContent) }

            var showCategoriesDialog by rememberSaveable { mutableStateOf(false) }
            var categoriesDialogResult: CategorySelection by rememberSaveable { mutableStateOf(CategorySelection()) }

            val mediaPreviewSheet = rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden,
                confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
                skipHalfExpanded = true,
                animationSpec = spring(1.4f)
            )

            val fullCollectionSheetState = rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden,
                confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
                skipHalfExpanded = true,
                animationSpec = spring(1.4f)
            )

            val categoriesRouter = object : CategoriesDialogRouter {
                val id: String = this::class.java.simpleName

                override fun showWithResult(onResult: (MediaCategory) -> Unit) {
                    showCategoriesDialog = true
                    categoriesDialogResult = CategorySelection(onResult)
                }

                override fun describeContents(): Int {
                    return 0
                }

                override fun writeToParcel(dest: Parcel, flags: Int) {
                    dest.writeString(id)
                }
            }

            val infoRouter = object : InfoRouter {
                val id: String = this::class.java.simpleName

                override fun show() {
                    showInfoDialog = true
                }

                override fun describeContents(): Int {
                    return 0
                }

                override fun writeToParcel(dest: Parcel, flags: Int) {
                    dest.writeString(id)
                }
            }
            val mediaPreviewRouter = object : MediaPreviewRouter {
                val id: String = this::class.java.simpleName

                override fun show(media: MediaItemUI) {
                    showMediaDetailSheet = MediaContent.PreviewMedia(media = media)
                }

                override fun describeContents(): Int {
                    return 0
                }

                override fun writeToParcel(dest: Parcel, flags: Int) {
                    dest.writeString(id)
                }
            }

            val fullCollectionRouter = object : FullCollectionsRouter {
                val id: String = this::class.java.simpleName

                override fun show(collection: CollectionNew) {
                    showFullCollectionsSheet = CollectionContent.Content(collection = collection)
                }

                override fun describeContents(): Int {
                    return 0
                }

                override fun writeToParcel(dest: Parcel, flags: Int) {
                    dest.writeString(id)
                }
            }

            val miniCollectionRouter = object : QuickCollectionsRouter {
                val id: String = this::class.java.simpleName

                override fun show(media: SingleMediaItem) {
                    quickCollectionDialog = MediaContent.PreviewMedia(media = MediaItemUI.from(media))
                    showMiniCollectionsDialog = true
                }

                override fun describeContents(): Int {
                    return 0
                }

                override fun writeToParcel(dest: Parcel, flags: Int) {
                    dest.writeString(id)
                }
            }

            LaunchedEffect(showMediaDetailSheet) {
                scope.launch {
                    when (showMediaDetailSheet) {
                        is MediaContent.PreviewMedia -> mediaPreviewSheet.show()
                        MediaContent.NoContent -> mediaPreviewSheet.hide()
                    }
                }
            }
            LaunchedEffect(showFullCollectionsSheet) {
                scope.launch {
                    when (showFullCollectionsSheet) {
                        is CollectionContent.Content -> fullCollectionSheetState.show()
                        CollectionContent.NoContent -> fullCollectionSheetState.hide()
                    }
                }
            }
            LaunchedEffect(mediaPreviewSheet) {
                snapshotFlow { mediaPreviewSheet.isVisible }.collect { isVisible ->
                    if (!isVisible) {
                        showMediaDetailSheet = MediaContent.NoContent
                    }
                }
            }
            LaunchedEffect(fullCollectionSheetState) {
                snapshotFlow { fullCollectionSheetState.isVisible }.collect { isVisible ->
                    if (!isVisible) showFullCollectionsSheet = CollectionContent.NoContent
                }
            }

            if (showInfoDialog) {
                MLAppInfoDialog(onDismiss = { showInfoDialog = false })
            }

            if (showCategoriesDialog) {
                CategoriesDialog(
                    state = mediaCategoriesState,
                    onDismiss = { showCategoriesDialog = false },
                    onSelection = { categorySelection -> categoriesDialogResult.result.invoke(categorySelection) }
                )
            }

            if (showMiniCollectionsDialog) {
                when (val currentState = quickCollectionDialog) {
                    is MediaContent.PreviewMedia -> {
                        MLQuickCollectionsDialog(
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
                fullCollectionRouter = fullCollectionRouter,
                categoryDialogRouter = categoriesRouter,
            )

            ModalBottomSheetLayout(
                sheetState = mediaPreviewSheet,
                sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                sheetContent = {
                    when (val currentMediaToShow = showMediaDetailSheet) {
                        is MediaContent.PreviewMedia -> {
                            MLDetailPreviewSheet(
                                mediaItem = currentMediaToShow.media,
                                onCloseClick = { scope.launch { mediaPreviewSheet.hide() } },
                                onMyListClick = { miniCollectionRouter.show(it) },
                            )
                        }

                        MediaContent.NoContent -> { /* TODO indicate that an error has occurred with a something went wrong UI state */
                        }
                    }
                }
            ) {
                ModalBottomSheetLayout(
                    sheetState = fullCollectionSheetState,
                    sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                    sheetContent = {
                        when (val currentCollectionToShow = showFullCollectionsSheet) {
                            is CollectionContent.Content -> {
                                when (val observeCollections = collectionsState) {
                                    is MyCollectionsContent -> {
                                        observeCollections
                                            .collections
                                            .find { it == currentCollectionToShow.collection }
                                            ?.also {
                                                MLCollectionDetail(
                                                    collection = it,
                                                    showMediaPreviewSheet = { item -> mediaPreviewRouter.show(MediaItemUI.from(item)) },
                                                    updateCollection = { collection -> collectionsViewModel.submit(UpdateCollection(collection)) },
                                                )
                                            }
                                    }

                                    else -> { /* TODO handle these cases */
                                    }
                                }
                            }

                            CollectionContent.NoContent -> { /* TODO indicate that an error has occurred with a something went wrong UI state */
                            }
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
}
