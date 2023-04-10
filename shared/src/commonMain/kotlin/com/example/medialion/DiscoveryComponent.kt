package com.example.medialion

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

// todo iewmodel is presentation logic, this should jsut be components that are exposing
//  data like FLows and that accepts actions. Sealead classes on what actions to take in the UI
//  should remain isolated in the client side.
//  READ - https://medium.com/better-programming/the-real-clean-architecture-in-android-modularization-e26940fd0a23
class DiscoveryComponent {
    private val client = HttpClient()

    suspend fun greetingFromClient(): String {
        val response = client.get("https://ktor.io/docs/")
        return response.bodyAsText()
    }
}