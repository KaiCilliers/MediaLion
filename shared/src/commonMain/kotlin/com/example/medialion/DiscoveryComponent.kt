package com.example.medialion

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

// todo iewmodel is presentation logic, this should jsut be components that are exposing
//  data like FLows and that accepts actions. Sealead classes on what actions to take in the UI
//  should remain isolated in the client side.
//  READ - https://medium.com/better-programming/the-real-clean-architecture-in-android-modularization-e26940fd0a23
class DiscoveryComponent {
    private val client = HttpClient()
    private val otherClient = Client()

    suspend fun greetingFromClient(): String {
        val response = client.get("https://ktor.io/docs/")
        return response.bodyAsText()
    }

    /*
    All Kotlin exceptions are unchecked, while Swift has only checked errors.
    Thus, to make your Swift code aware of expected exceptions, Kotlin
    functions should be marked with the @Throws annotation specifying a list
    of potential exception classes.
     */
    @Throws(Exception::class)
    suspend fun allLaunches(): String {
        return otherClient.getAllLaunches().map {
            it.missionName + " Success: ${it.launchSuccess}\n"
        }.take(10).toString()
    }
}

class Client {
    private val httpClient = HttpClient {
        /*
        This code uses the Ktor ContentNegotiation plugin to deserialize
        the GET request result. The plugin processes the request and the
        response payload as JSON, serializing and deserializing them
        using a special serializer.
         */
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    suspend fun getAllLaunches(): List<RocketLaunch> {
        return httpClient.get("https://api.spacexdata.com/v5/launches").body()
    }
}

@Serializable
data class RocketLaunch(
    @SerialName("flight_number")
    val flightNumber: Int,
    @SerialName("name")
    val missionName: String,
    @SerialName("date_utc")
    val launchDateUTC: String,
    @SerialName("details")
    val details: String?,
    @SerialName("success")
    val launchSuccess: Boolean?,
    @SerialName("links")
    val links: Links
)

@Serializable
data class Links(
    @SerialName("patch")
    val patch: Patch?,
    @SerialName("article")
    val article: String?
)

@Serializable
data class Patch(
    @SerialName("small")
    val small: String?,
    @SerialName("large")
    val large: String?
)