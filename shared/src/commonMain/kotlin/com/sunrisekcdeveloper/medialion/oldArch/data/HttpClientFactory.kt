package com.sunrisekcdeveloper.medialion.oldArch.data

import io.ktor.client.HttpClient

expect class HttpClientFactory {
    fun create(): HttpClient
}