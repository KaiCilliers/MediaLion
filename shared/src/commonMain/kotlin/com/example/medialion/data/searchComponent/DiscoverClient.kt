package com.example.medialion.data.searchComponent

import com.example.medialion.data.NetworkConstants
import com.example.medialion.data.extensions.safeApiCall
import com.example.medialion.data.extensions.standardParameters
import com.example.medialion.data.models.PagedMovieResults
import com.example.medialion.data.models.PagedTVShowResults
import com.example.medialion.domain.models.ResultOf
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineDispatcher

interface DiscoverClient {
    suspend fun discoverMovies(): ResultOf<PagedMovieResults>
    suspend fun discoverTvShows(): ResultOf<PagedTVShowResults>

    class Default(
        private val httpClient: HttpClient,
        private val dispatcher: CoroutineDispatcher,
    ) : DiscoverClient {
        override suspend fun discoverMovies(): ResultOf<PagedMovieResults> = dispatcher.safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/discover/movie") {
                url {
                    parameters.apply {
                        standardParameters()
                    }
                }
            }.body()
        }

        override suspend fun discoverTvShows(): ResultOf<PagedTVShowResults> = dispatcher.safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/discover/tv") {
                url {
                    parameters.apply {
                        standardParameters()
                    }
                }
            }
        }
    }
}