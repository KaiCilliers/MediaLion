package com.example.medialion.data.searchComponent

import com.example.medialion.data.NetworkConstants
import com.example.medialion.domain.models.ResultOf
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.utils.io.errors.IOException

interface TMDBClient {
    suspend fun searchMovies(query: String): ResultOf<PagedMediaResults>
    suspend fun relatedMovies(movieId: Int): ResultOf<PagedMediaResults>
    suspend fun topRatedMovies(): ResultOf<PagedMediaResults>
    class Default(
        private val httpClient: HttpClient
    ) : TMDBClient {
        override suspend fun searchMovies(query: String): ResultOf<PagedMediaResults> {
            return try {
                val response: PagedMediaResults = httpClient.get(NetworkConstants.BASE_URL_TMDB + "/search/movie") {
                    url {
                        parameters.apply {
                            append(
                                NetworkConstants.FIELD_API_KEY,
                                "9b3b6234bb46dbbd68fedc64b4d46e63"
                            )
                            append(
                                NetworkConstants.FIELD_LANGUAGE,
                                "en-US"
                            )
                            append(
                                NetworkConstants.FIELD_QUERY,
                                query
                            )
                            append(
                                NetworkConstants.FIELD_PAGE,
                                "1"
                            )
                            append(
                                NetworkConstants.FIELD_INCLUDE_ADULT,
                                "false"
                            )
                        }
                    }
                }.body()

                ResultOf.Success(response)
            } catch (ioe: IOException) {
                println("deadpool - ${ioe.cause}")
                ResultOf.Failure("[IO] error please retry", ioe)
            } catch (ex: Exception) {
                println("deadpool - ${ex.cause}")
                ResultOf.Failure("[Exception] error please retry", ex)
            }
        }

        override suspend fun relatedMovies(movieId: Int): ResultOf<PagedMediaResults> {
            return try {
                val response: PagedMediaResults = httpClient.get(NetworkConstants.BASE_URL_TMDB + "/movie/$movieId/similar") {
                    url {
                        parameters.apply {
                            append(
                                NetworkConstants.FIELD_API_KEY,
                                "9b3b6234bb46dbbd68fedc64b4d46e63"
                            )
                            append(
                                NetworkConstants.FIELD_LANGUAGE,
                                "en-US"
                            )
                            append(
                                NetworkConstants.FIELD_PAGE,
                                "1"
                            )
                            append(
                                NetworkConstants.FIELD_INCLUDE_ADULT,
                                "false"
                            )
                        }
                    }
                }.body()

                ResultOf.Success(response)
            } catch (ioe: IOException) {
                println("deadpool - ${ioe.cause}")
                ResultOf.Failure("[IO] error please retry", ioe)
            } catch (ex: Exception) {
                println("deadpool - ${ex.cause}")
                ResultOf.Failure("[Exception] error please retry", ex)
            }
        }

        override suspend fun topRatedMovies(): ResultOf<PagedMediaResults> {
            return try {
                val response: PagedMediaResults = httpClient.get(NetworkConstants.BASE_URL_TMDB + "/movie/top_rated") {
                    url {
                        parameters.apply {
                            append(
                                NetworkConstants.FIELD_API_KEY,
                                "9b3b6234bb46dbbd68fedc64b4d46e63"
                            )
                            append(
                                NetworkConstants.FIELD_LANGUAGE,
                                "en-US"
                            )
                            append(
                                NetworkConstants.FIELD_PAGE,
                                "1"
                            )
                            append(
                                NetworkConstants.FIELD_INCLUDE_ADULT,
                                "false"
                            )
                        }
                    }
                }.body()

                ResultOf.Success(response)

            } catch (ioe: IOException) {
                println("deadpool - ${ioe.cause}")
                ResultOf.Failure("[IO] error please retry", ioe)
            } catch (ex: Exception) {
                println("deadpool - ${ex.cause}")
                ResultOf.Failure("[Exception] error please retry", ex)
            }
        }
    }
}

