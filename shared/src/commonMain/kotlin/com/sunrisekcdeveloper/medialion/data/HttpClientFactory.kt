package com.sunrisekcdeveloper.medialion.data

import io.ktor.client.HttpClient

expect class HttpClientFactory {
    fun create(): HttpClient
}