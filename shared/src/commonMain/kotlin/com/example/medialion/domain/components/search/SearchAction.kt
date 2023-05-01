package com.example.medialion.domain.components.search

sealed class SearchAction {
    object ClearSearchText : SearchAction()
    data class SubmitSearchQuery(
        val query: String
    ) : SearchAction()
    data class RemoveFromFavorites(
        val movieId: Int,
    ): SearchAction()
    data class AddToFavorites(
        val movieId: Int
    ): SearchAction()
    data class GetMovieDetails(
        val movieId: Int
    ): SearchAction()
    data class RemoveFromCollection(
        val collectionName: String,
        val movieId: Int,
    ): SearchAction()
    data class AddToCollection(
        val collectionName: String,
        val movieId: Int
    ): SearchAction()
    data class CreateCollection(
        val collectionName: String
    ) : SearchAction()
}