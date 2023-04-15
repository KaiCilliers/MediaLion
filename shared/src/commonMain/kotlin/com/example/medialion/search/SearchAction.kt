package com.example.medialion.search

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
}