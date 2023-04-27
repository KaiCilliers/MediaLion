package com.example.medialion.data.searchComponent

import com.example.medialion.data.NetworkConstants
import com.example.medialion.data.extensions.safeApiCall
import com.example.medialion.data.extensions.standardParameters
import com.example.medialion.data.models.PagedMovieResults
import com.example.medialion.data.models.PagedMultiResults
import com.example.medialion.data.models.PagedPersonResults
import com.example.medialion.data.models.PagedTVShowResults
import com.example.medialion.domain.models.ResultOf
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineDispatcher

interface TMDBClient {
    suspend fun relatedMovies(movieId: Int): ResultOf<PagedMovieResults>
    suspend fun topRatedMovies(): ResultOf<PagedMovieResults>

    // region search
    suspend fun multiSearch(query: String): ResultOf<PagedMultiResults>
    suspend fun searchTvShows(query: String): ResultOf<PagedTVShowResults>
    suspend fun searchPersons(query: String): ResultOf<PagedPersonResults>
    suspend fun searchMovies(query: String): ResultOf<PagedMovieResults>

    //endregion
    class Default(
        private val httpClient: HttpClient,
        private val dispatcher: CoroutineDispatcher,
    ) : TMDBClient {
        override suspend fun searchMovies(query: String): ResultOf<PagedMovieResults> =
            dispatcher.safeApiCall {
                httpClient.get(NetworkConstants.BASE_URL_TMDB + "/search/movie") {
                    url {
                        parameters.apply {
                            standardParameters()
                            append(
                                NetworkConstants.FIELD_QUERY,
                                query
                            )
                            append(
                                NetworkConstants.FIELD_PAGE,
                                "1"
                            )
                        }
                    }
                }.body()
            }

        override suspend fun relatedMovies(movieId: Int): ResultOf<PagedMovieResults> =
            dispatcher.safeApiCall {
                httpClient.get(NetworkConstants.BASE_URL_TMDB + "/movie/$movieId/similar") {
                    url {
                        parameters.apply {
                            standardParameters()
                            append(
                                NetworkConstants.FIELD_PAGE,
                                "1"
                            )
                        }
                    }
                }.body()
            }

        override suspend fun topRatedMovies(): ResultOf<PagedMovieResults> =
            dispatcher.safeApiCall {
                httpClient.get(NetworkConstants.BASE_URL_TMDB + "/movie/top_rated") {
                    url {
                        parameters.apply {
                            standardParameters()
                            append(NetworkConstants.FIELD_PAGE, "1")
                        }
                    }
                }.body()
            }

        override suspend fun multiSearch(query: String): ResultOf<PagedMultiResults> =
            dispatcher.safeApiCall {
                httpClient.get(NetworkConstants.BASE_URL_TMDB + "/search/multi") {
                    url {
                        parameters.apply {
                            standardParameters()
                            append(NetworkConstants.FIELD_PAGE, "1")
                            append(NetworkConstants.FIELD_QUERY, query)
                        }
                    }
                }.body()
            }

        override suspend fun searchTvShows(query: String): ResultOf<PagedTVShowResults> =
            dispatcher.safeApiCall {
                httpClient.get(NetworkConstants.BASE_URL_TMDB + "/search/tv") {
                    url {
                        parameters.apply {
                            standardParameters()
                            append(NetworkConstants.FIELD_PAGE, "1")
                            append(NetworkConstants.FIELD_QUERY, query)
                        }
                    }
                }.body()
            }

        override suspend fun searchPersons(query: String): ResultOf<PagedPersonResults> =
            dispatcher.safeApiCall {
                httpClient.get(NetworkConstants.BASE_URL_TMDB + "/search/person") {
                    url {
                        parameters.apply {
                            standardParameters()
                            append(NetworkConstants.FIELD_PAGE, "1")
                            append(NetworkConstants.FIELD_QUERY, query)
                        }
                    }
                }.body()
            }
    }
}


