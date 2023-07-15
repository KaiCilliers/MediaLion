package com.sunrisekcdeveloper.medialion.newarch.components.shared.data.mediaCategory

import com.sunrisekcdeveloper.medialion.clients.models.GenreResponse
import com.sunrisekcdeveloper.medialion.clients.models.GenreWrapper
import com.sunrisekcdeveloper.medialion.clients.standardParameters
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models.MediaTypeNew
import com.sunrisekcdeveloper.medialion.newarch.components.shared.data.models.TMDBUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class TMDBClientNew(
    private val httpClient: HttpClient,
) : CategoryRemoteClient {
    override suspend fun allCategories(): List<MediaCategoryApiDto> {
        val movieGenresEndpoint = TMDBUrl("/genre/movie/list")
        val tvShowGenresEndpoint = TMDBUrl("/genre/tv/list")

        return coroutineScope {
            val movieGenresResponse = async { exeRequest(movieGenresEndpoint) }
            val tvShowGenresResponse = async { exeRequest(tvShowGenresEndpoint) }

            val allGenres: MutableList<MediaCategoryApiDto> = movieGenresResponse
                .await()
                .map { response -> mapToDto(response, MediaTypeNew.Movie) }
                .toMutableList()

            tvShowGenresResponse
                .await()
                .map { response -> mapToDto(response, MediaTypeNew.TVShow) }
                .forEach { mediaCategoryApiDto ->
                    val matchingGenreIndex = allGenres.indexOfFirst { mediaCategoryApiDto == it }
                    if (matchingGenreIndex >= 0) {
                        val matchingGenre = allGenres[matchingGenreIndex]
                        allGenres[matchingGenreIndex] = matchingGenre.copy(appliesTo = MediaTypeNew.All)
                    } else allGenres.add(mediaCategoryApiDto)
                }

            allGenres
        }
    }

    private fun mapToDto(genreResponse: GenreResponse, appliesTo: MediaTypeNew): MediaCategoryApiDto {
        return MediaCategoryApiDto(
            id = genreResponse.id.toString(),
            name = genreResponse.name,
            appliesTo = appliesTo
        )
    }

    private suspend fun exeRequest(endpoint: TMDBUrl): List<GenreResponse> {
        return httpClient.get(endpoint.toString()) {
            url { parameters.standardParameters() }
        }
            .body<GenreWrapper>()
            .genres
    }
}