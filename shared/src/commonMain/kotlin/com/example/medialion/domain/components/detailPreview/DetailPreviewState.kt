package com.example.medialion.domain.components.detailPreview

import com.example.medialion.domain.models.MovieUiModel

sealed class DetailPreviewState {
    data class Details(
        val media: MovieUiModel
    ) : DetailPreviewState()
}