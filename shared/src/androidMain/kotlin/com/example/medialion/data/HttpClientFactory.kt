package com.example.medialion.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.HttpSendPipeline
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

actual class HttpClientFactory {
    @OptIn(ExperimentalSerializationApi::class)
    actual fun create(): HttpClient {
        val client = HttpClient(Android) {
            engine {
                connectTimeout = 30_000
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        useAlternativeNames = false
                        explicitNulls = false
                        isLenient = true
                    }
                )
            }
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.SIMPLE
            }
            // todo how to handle URL for images?
//            defaultRequest {
//                url {
//                    protocol = URLProtocol.HTTPS
//                    host = NetworkConstants.BASE_URL_TMDB
//                }
//            }
        }
        client.sendPipeline.intercept(HttpSendPipeline.State) {
            context.headers.append("ClientPlatform", "Android")
        }
        return client
    }
}