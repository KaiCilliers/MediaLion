package com.example.medialion.data.searchComponent

import com.example.medialion.data.NetworkConstants
import com.example.medialion.domain.models.ResultOf
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface TMDBClient {
    suspend fun searchMovies(query: String): ResultOf<PagedMediaResults>
    suspend fun relatedMovies(movieId: Int): ResultOf<PagedMediaResults>
    suspend fun topRatedMovies(): ResultOf<PagedMediaResults>

    class Default(
        private val httpClient: HttpClient,
        private val dispatcher: CoroutineDispatcher,
    ) : TMDBClient {
        override suspend fun searchMovies(query: String): ResultOf<PagedMediaResults> = dispatcher.safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/search/movie") {
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
                    }
                }
            }.body()
        }

        override suspend fun relatedMovies(movieId: Int): ResultOf<PagedMediaResults> = dispatcher.safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/movie/$movieId/similar") {
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
        }

        override suspend fun topRatedMovies(): ResultOf<PagedMediaResults> = dispatcher.safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/movie/top_rated") {
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
        }
    }
}

suspend inline fun <reified T> CoroutineDispatcher.safeApiCall(crossinline request: suspend () -> HttpResponse): ResultOf<T> =
    withContext(this) {
        return@withContext try {
           val response: T = request().body()
            ResultOf.Success(response)
        } catch (ioe: IOException) {
            println("deadpool - ${ioe.cause}")
            ResultOf.Failure("[IO] error please retry", ioe)
        } catch (ex: Exception) {
            println("deadpool - ${ex.cause}")
            ResultOf.Failure("[Exception] error please retry", ex)
        }
    }


