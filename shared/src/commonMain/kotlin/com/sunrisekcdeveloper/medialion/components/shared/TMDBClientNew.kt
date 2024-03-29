package com.sunrisekcdeveloper.medialion.components.shared

import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaRequirements
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaTypeNew
import com.sunrisekcdeveloper.medialion.components.shared.data.mediaCategory.CategoryRemoteClient
import com.sunrisekcdeveloper.medialion.components.shared.data.mediaCategory.MediaCategoryApiDto
import com.sunrisekcdeveloper.medialion.components.shared.data.models.TMDBUrl
import com.sunrisekcdeveloper.medialion.components.shared.data.singleMedia.MediaRemoteClient
import com.sunrisekcdeveloper.medialion.components.shared.data.singleMedia.SingleMediaApiDto
import com.sunrisekcdeveloper.medialion.network.models.GenreResponse
import com.sunrisekcdeveloper.medialion.network.models.GenreWrapper
import com.sunrisekcdeveloper.medialion.network.models.MediaResponse
import com.sunrisekcdeveloper.medialion.network.models.PagedMediaResponse
import com.sunrisekcdeveloper.medialion.utils.debug
import com.sunrisekcdeveloper.medialion.utils.mappers.Mapper
import com.sunrisekcdeveloper.medialion.utils.standardParameters
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.take

class TMDBClientNew(
    private val httpClient: HttpClient,
    private val mapper: Mapper<MediaResponse, SingleMediaApiDto>,
) : CategoryRemoteClient , MediaRemoteClient {
    override suspend fun allCategories(): List<MediaCategoryApiDto> {
        val movieGenresEndpoint = TMDBUrl("/genre/movie/list")
        val tvShowGenresEndpoint = TMDBUrl("/genre/tv/list")

        return coroutineScope {
            val movieGenresRequest = async { exeRequest(movieGenresEndpoint) }
            val tvShowGenresRequest = async { exeRequest(tvShowGenresEndpoint) }

            val allGenres: MutableList<MediaCategoryApiDto> = mutableListOf<MediaCategoryApiDto>().apply {

                val movieGenreResponse = movieGenresRequest.await()
                val tvShowGenreResponse = tvShowGenresRequest.await()

                val commonGenres = movieGenreResponse.filter { tvShowGenreResponse.contains(it) }

                val movieGenreResponseFiltered = movieGenreResponse.filterNot { commonGenres.contains(it) }
                val tvShowGenreResponseFiltered = tvShowGenreResponse.filterNot { commonGenres.contains(it) }

                commonGenres
                    .map { genre -> mapToDto(genre, MediaTypeNew.All) }
                    .also { addAll(it) }

                movieGenreResponseFiltered
                    .map { response -> mapToDto(response, MediaTypeNew.Movie) }
                    .also { addAll(it) }

                tvShowGenreResponseFiltered
                    .map { response -> mapToDto(response, MediaTypeNew.TVShow) }
                    .also { addAll(it) }

                sortBy { it.name }
            }
            allGenres
        }
    }

    override fun search(requirements: MediaRequirements): Flow<SingleMediaApiDto> = flow {
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
                .mapNotNull { mediaResponse ->
                    runCatching { mapper.map(mediaResponse) }.getOrElse {
                        Napier.w(it) { "Failed to map remote media model to api model [$mediaResponse]" }
                        null
                    }
                }
                .forEach { singleMediaNetworkDto ->  emit(singleMediaNetworkDto) }

        } while (page <= totalPages)
    }

    override fun discover(requirements: MediaRequirements): Flow<SingleMediaApiDto> = flow {
        when(requirements.withMediaType) {
            MediaTypeNew.All -> {
                val movieDiscoveryFlow = discoverEndpoint(MediaTypeNew.Movie, requirements).take(requirements.amountOfMedia)
                val tvShowDiscoveryFlow = discoverEndpoint(MediaTypeNew.TVShow, requirements).take(requirements.amountOfMedia)
                emitAll(merge(movieDiscoveryFlow, tvShowDiscoveryFlow))
            }
            MediaTypeNew.Movie -> {
                emitAll(
                    discoverEndpoint(MediaTypeNew.Movie, requirements)
                        .take(requirements.amountOfMedia)
                )
            }
            MediaTypeNew.TVShow -> {
                emitAll(
                    discoverEndpoint(MediaTypeNew.TVShow, requirements)
                        .take(requirements.amountOfMedia)
                )
            }
        }
    }

    private suspend fun discoverEndpoint(typeNew: MediaTypeNew, requirements: MediaRequirements): Flow<SingleMediaApiDto> = flow {
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
                    val genreIds = requirements.withCategories.map { it.identifier().uniqueIdentifier().toInt() }
                    parameters.append("with_genres", genreIds.joinToString(separator = ","))
                }
            }.body<PagedMediaResponse>()

            totalPages = pagedMediaResponse.totalPages
            pagedMediaResponse
                .results
                .mapNotNull {  mediaResponse ->
                    runCatching { mapper.map(mediaResponse) }.getOrElse {
                        Napier.w(it) { "Failed to map response media item to api model [$mediaResponse]" }
                        null
                    }
                }
                .forEach { singleMediaNetworkDto -> emit(singleMediaNetworkDto) }

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