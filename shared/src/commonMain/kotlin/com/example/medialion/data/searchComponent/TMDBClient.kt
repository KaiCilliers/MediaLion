package com.example.medialion.data.searchComponent

import com.example.medialion.data.NetworkConstants
import com.example.medialion.domain.models.ResultOf
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import io.ktor.http.path
import io.ktor.utils.io.errors.IOException

// 9b3b6234bb46dbbd68fedc64b4d46e63
interface TMDBClient {
    suspend fun multiSearch(query: String): ResultOf<PagedMediaResults>
    class Default(
        private val httpClient: HttpClient
    ) : TMDBClient {
        override suspend fun multiSearch(query: String): ResultOf<PagedMediaResults> {
            return try {
                val response: PagedMediaResults = httpClient.get(NetworkConstants.BASE_URL_TMDB + "/search/multi") {
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
            } catch (e: IOException) {
                ResultOf.Failure("[IO] exception", e.cause)
            }
        }
    }
}

