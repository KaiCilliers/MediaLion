package com.sunrisekcdeveloper.medialion.components.shared.data.models

class TMDBUrl(private val origin: String) {

    private val BASE_URL_TMDB = "https://api.themoviedb.org/3"

    override fun toString(): String {
        return BASE_URL_TMDB + origin
    }
}

class TMDBImageUrl(private val origin: String) {

    private val BASE_URL_TMDB = "https://image.tmdb.org/t/p/w500"

    override fun toString(): String {
        return BASE_URL_TMDB + origin
    }
}