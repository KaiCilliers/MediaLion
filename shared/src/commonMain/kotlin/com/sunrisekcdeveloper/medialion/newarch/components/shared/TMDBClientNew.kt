package com.sunrisekcdeveloper.medialion.newarch.components.shared

import com.sunrisekcdeveloper.medialion.oldArch.clients.models.GenreResponse
import com.sunrisekcdeveloper.medialion.oldArch.clients.models.GenreWrapper
import com.sunrisekcdeveloper.medialion.oldArch.clients.models.MediaResponse
import com.sunrisekcdeveloper.medialion.oldArch.clients.models.PagedMediaResponse
import com.sunrisekcdeveloper.medialion.oldArch.clients.standardParameters
import com.sunrisekcdeveloper.medialion.oldArch.mappers.Mapper
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models.MediaRequirements
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models.MediaTypeNew
import com.sunrisekcdeveloper.medialion.newarch.components.shared.data.mediaCategory.CategoryRemoteClient
import com.sunrisekcdeveloper.medialion.newarch.components.shared.data.mediaCategory.MediaCategoryApiDto
import com.sunrisekcdeveloper.medialion.newarch.components.shared.data.models.TMDBUrl
import com.sunrisekcdeveloper.medialion.newarch.components.shared.data.singleMedia.MediaRemoteClient
import com.sunrisekcdeveloper.medialion.newarch.components.shared.data.singleMedia.SingleMediaNetworkDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge

class TMDBClientNew(
    private val httpClient: HttpClient,
    private val mapper: Mapper<MediaResponse, SingleMediaNetworkDto>,
) : CategoryRemoteClient , MediaRemoteClient {
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

    override fun search(requirements: MediaRequirements): Flow<SingleMediaNetworkDto> = flow {
        val searchRequest = TMDBUrl(
            origin = when(requirements.withMediaType) {
                MediaTypeNew.All -> "/search/multi"
                MediaTypeNew.Movie -> "/search/movie"
                MediaTypeNew.TVShow -> "/search/tv"
            }
        )

        var page = 1
        var totalPages: Int

        do {

            val pagedMediaResponse = httpClient.get(searchRequest.toString()) {
                url {
                    parameters.standardParameters()
                    parameters.append("query", requirements.withText)
                    parameters.append("page", "${page++}")
                }
            }.body<PagedMediaResponse>()

            totalPages = pagedMediaResponse.totalPages
            pagedMediaResponse
                .results
                .map { mapper.map(it) }
                .forEach { singleMediaNetworkDto ->  emit(singleMediaNetworkDto) }

        } while (page <= totalPages)
    }

    override fun discover(requirements: MediaRequirements): Flow<SingleMediaNetworkDto> = flow {
        when(requirements.withMediaType) {
            MediaTypeNew.All -> {
                val movieDiscoveryFlow = discoverEndpoint(MediaTypeNew.Movie, requirements)
                val tvShowDiscoveryFlow = discoverEndpoint(MediaTypeNew.TVShow, requirements)
                emitAll(merge(movieDiscoveryFlow, tvShowDiscoveryFlow))
            }
            MediaTypeNew.Movie -> {
                emitAll(discoverEndpoint(MediaTypeNew.Movie, requirements))
            }
            MediaTypeNew.TVShow -> {
                discoverEndpoint(MediaTypeNew.TVShow, requirements)
            }
        }
    }

    private suspend fun discoverEndpoint(typeNew: MediaTypeNew, requirements: MediaRequirements): Flow<SingleMediaNetworkDto> = flow {
        val requestUrl = when(typeNew) {
            MediaTypeNew.Movie -> TMDBUrl("/discover/movie")
            MediaTypeNew.TVShow -> TMDBUrl("/discover/tv")
            else -> throw Exception("Unsupported media type for discovery endpoint $typeNew")
        }

        var page = 1
        var totalPages: Int

        do {

            val pagedMediaResponse = httpClient.get(requestUrl.toString()) {
                url {
                    parameters.standardParameters()
                    parameters.append("page", "${page++}")
                }
            }.body<PagedMediaResponse>()

            totalPages = pagedMediaResponse.totalPages
            pagedMediaResponse
                .results
                .map { mapper.map(it) }
                .forEach { singleMediaNetworkDto ->  emit(singleMediaNetworkDto) }

        } while (page <= totalPages)
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