package com.sunrisekcdeveloper.medialion.network

import io.ktor.client.HttpClient

expect class HttpClientFactory {
    fun create(): HttpClient
}