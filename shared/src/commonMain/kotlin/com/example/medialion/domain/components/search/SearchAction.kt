package com.example.medialion.domain.components.search

import com.example.medialion.domain.models.MediaType

sealed class SearchAction {
    object ClearSearchText : SearchAction()
    data class SubmitSearchQuery(
        val query: String
    ) : SearchAction()
    data class RemoveFromFavorites(
        val movieId: Int,
        val mediaType: MediaType,
    ): SearchAction()
    data class AddToFavorites(
        val mediaId: Int,
        val mediaType: MediaType,
    ): SearchAction()
    data class GetMediaDetails(
        val mediaId: Int,
        val mediaType: MediaType,
    ): SearchAction()
    data class RemoveFromCollection(
        val collectionName: String,
        val mediaId: Int,
        val mediaType: MediaType,
    ): SearchAction()
    data class AddToCollection(
        val collectionName: String,
        val mediaId: Int,
        val mediaType: MediaType,
    ): SearchAction()
    data class CreateCollection(
        val collectionName: String
    ) : SearchAction()
}