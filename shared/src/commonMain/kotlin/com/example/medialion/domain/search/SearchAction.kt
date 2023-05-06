package com.example.medialion.domain.search

import com.example.medialion.domain.MediaType
import com.example.medialion.domain.value.ID
import com.example.medialion.domain.value.Title

sealed class SearchAction {
    object ClearSearchText : SearchAction()
    data class SubmitSearchQuery(
        val query: String
    ) : SearchAction()
    data class RemoveFromFavorites(
        val movieId: ID,
        val mediaType: MediaType,
    ): SearchAction()
    data class AddToFavorites(
        val mediaId: ID,
        val mediaType: MediaType,
    ): SearchAction()
    data class GetMediaDetails(
        val mediaId: ID,
        val mediaType: MediaType,
    ): SearchAction()
    data class RemoveFromCollection(
        val collectionName: Title,
        val mediaId: ID,
        val mediaType: MediaType,
    ): SearchAction()
    data class AddToCollection(
        val collectionName: Title,
        val mediaId: ID,
        val mediaType: MediaType,
    ): SearchAction()
    data class CreateCollection(
        val collectionName: Title
    ) : SearchAction()
}