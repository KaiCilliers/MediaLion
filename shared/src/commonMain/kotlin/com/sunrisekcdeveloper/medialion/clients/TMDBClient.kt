package com.sunrisekcdeveloper.medialion.clients

import com.sunrisekcdeveloper.medialion.data.NetworkConstants
import com.sunrisekcdeveloper.medialion.clients.models.GenreResponse
import com.sunrisekcdeveloper.medialion.clients.models.MediaResponse
import com.sunrisekcdeveloper.medialion.clients.models.PagedMediaResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface TMDBClient {

    // region search
    suspend fun multiSearch(query: String, page: Int): Result<MediaResponse>
    suspend fun searchTvShows(query: String, page: Int): Result<PagedMediaResponse>
    suspend fun searchMovies(query: String, page: Int): Result<PagedMediaResponse>
    //endregion

    // region genre
    suspend fun movieGenres(): Result<GenreResponse>
    suspend fun tvGenres(): Result<GenreResponse>
    // endregion

    // region movie
    suspend fun movieDetails(id: Int): Result<MediaResponse>
    suspend fun recommendationsForMovie(id: Int, page: Int): Result<PagedMediaResponse>
    suspend fun similarForMovie(id: Int, page: Int): Result<PagedMediaResponse>
    suspend fun moviesNowInTheatres(page: Int): Result<PagedMediaResponse>
    suspend fun topRatedMovies(page: Int): Result<PagedMediaResponse>
    suspend fun popularMovies(page: Int): Result<PagedMediaResponse>
    suspend fun moviesComingToTheatres(page: Int): Result<PagedMediaResponse>
    // endregion

    // region trending
    suspend fun trendingMedia(): Result<PagedMediaResponse>
    // endregion

    // region tv
    suspend fun tvDetails(id: Int): Result<MediaResponse>
    suspend fun recommendationsForTv(id: Int, page: Int): Result<PagedMediaResponse>
    suspend fun similarForTv(id: Int, page: Int): Result<PagedMediaResponse>
    suspend fun tvAiringToday(page: Int): Result<PagedMediaResponse>
    suspend fun topRatedTv(page: Int): Result<PagedMediaResponse>
    suspend fun popularTv(page: Int): Result<PagedMediaResponse>
    suspend fun tvOnAirComingWeek(page: Int): Result<PagedMediaResponse>
    // endregion

    // region discover
    suspend fun discoverTv(genreId: Int, page: Int): Result<PagedMediaResponse>
    suspend fun discoverMovie(genreId: Int, page: Int): Result<PagedMediaResponse>
    // endregion

    class Default(private val httpClient: HttpClient) : TMDBClient {

        override suspend fun multiSearch(query: String, page: Int): Result<MediaResponse> {
            val endpoint = NetworkConstants.BASE_URL_TMDB + "/search/multi"
            return this.suspendRunReThrowable("Failed to fetch media [query=$query, page=$page, endpoint=$endpoint]") {
                httpClient.get(endpoint) {
                    url {
                        parameters.apply {
                            standardParameters()
                            append(NetworkConstants.FIELD_QUERY, query)
                            append(NetworkConstants.FIELD_PAGE, page.toString())
                        }
                    }
                }.body()
            }
        }

        override suspend fun searchTvShows(query: String, page: Int): Result<PagedMediaResponse> {
            val endpoint = NetworkConstants.BASE_URL_TMDB + "/search/tv"
            return this.suspendRunReThrowable("Failed to fetch tv shows [query=$query, page=$page, endpoint=$endpoint]") {
                httpClient.get(endpoint) {
                    url {
                        parameters.apply {
                            standardParameters()
                            append(NetworkConstants.FIELD_QUERY, query)
                            append(NetworkConstants.FIELD_PAGE, page.toString())
                        }
                    }
                }.body()
            }
        }

        override suspend fun searchMovies(query: String, page: Int): Result<PagedMediaResponse> {
            val endpoint = NetworkConstants.BASE_URL_TMDB + "/search/movie"
            return this.suspendRunReThrowable("Failed to fetch movies [query=$query, page=$page, endpoint=$endpoint]") {
                httpClient.get(endpoint) {
                    url {
                        parameters.apply {
                            standardParameters()
                            append(NetworkConstants.FIELD_QUERY, query)
                            append(NetworkConstants.FIELD_PAGE, page.toString())
                        }
                    }
                }.body()
            }
        }

        override suspend fun movieGenres(): Result<GenreResponse> {
            val endpoint = NetworkConstants.BASE_URL_TMDB + "/genre/movie/list"
            return this.suspendRunReThrowable("Failed to fetch movie genres [endpoint=$endpoint]") {
                httpClient.get(endpoint).body()
            }
        }

        override suspend fun tvGenres(): Result<GenreResponse> {
            val endpoint = NetworkConstants.BASE_URL_TMDB + "/genre/tv/list"
            return this.suspendRunReThrowable("Failed to fetch tv genres [endpoint=$endpoint]") {
                httpClient.get(endpoint).body()
            }
        }

        override suspend fun movieDetails(id: Int): Result<MediaResponse> {
            return this.suspendRunReThrowable("Failed to fetch movie details [movieId=$id]") {
                httpClient.get(NetworkConstants.BASE_URL_TMDB + "/movie/$id") {
                    url { parameters.standardParameters() }
                }.body()
            }
        }

        override suspend fun recommendationsForMovie(
            id: Int,
            page: Int
        ): Result<PagedMediaResponse> {
            val endpoint = NetworkConstants.BASE_URL_TMDB + "/movie/$id/recommendations"
            return this.suspendRunReThrowable("Failed to fetch movies [id=$id, page=$page, endpoint=$endpoint]") {
                httpClient.get(endpoint) {
                    url {
                        parameters.apply {
                            standardParameters()
                            append(NetworkConstants.FIELD_PAGE, page.toString())
                        }
                    }
                }.body()
            }
        }

        override suspend fun similarForMovie(id: Int, page: Int): Result<PagedMediaResponse> {
            val endpoint = NetworkConstants.BASE_URL_TMDB + "/movie/$id/similar"
            return this.suspendRunReThrowable("Failed to fetch movies [id=$id, page=$page, endpoint=$endpoint]") {
                httpClient.get(endpoint) {
                    url {
                        parameters.apply {
                            standardParameters()
                            append(NetworkConstants.FIELD_PAGE, page.toString())
                        }
                    }
                }.body()
            }
        }

        override suspend fun moviesNowInTheatres(page: Int): Result<PagedMediaResponse> {
            val endpoint = NetworkConstants.BASE_URL_TMDB + "/movie/now_playing"
            return this.suspendRunReThrowable("Failed to fetch movies [page=$page, endpoint=$endpoint]") {
                httpClient.get(endpoint) {
                    url {
                        parameters.apply {
                            standardParameters()
                            append(NetworkConstants.FIELD_PAGE, page.toString())
                        }
                    }
                }.body()
            }
        }

        override suspend fun topRatedMovies(page: Int): Result<PagedMediaResponse> {
            val endpoint = NetworkConstants.BASE_URL_TMDB + "/movie/top_rated"
            return this.suspendRunReThrowable("Failed to fetch movies [page=$page, endpoint=$endpoint]") {
                httpClient.get(endpoint) {
                    url {
                        parameters.apply {
                            standardParameters()
                            append(NetworkConstants.FIELD_PAGE, page.toString())
                        }
                    }
                }.body()
            }
        }

        override suspend fun popularMovies(page: Int): Result<PagedMediaResponse> {
            val endpoint = NetworkConstants.BASE_URL_TMDB + "/movie/popular"
            return this.suspendRunReThrowable("Failed to fetch movies [page=$page, endpoint=$endpoint]") {
                httpClient.get(endpoint) {
                    url {
                        parameters.apply {
                            standardParameters()
                            append(NetworkConstants.FIELD_PAGE, page.toString())
                        }
                    }
                }.body()
            }
        }

        override suspend fun moviesComingToTheatres(page: Int): Result<PagedMediaResponse> {
            val endpoint = NetworkConstants.BASE_URL_TMDB + "/movie/upcoming"
            return this.suspendRunReThrowable("Failed to fetch movies [page=$page, endpoint=$endpoint]") {
                httpClient.get(endpoint) {
                    url {
                        parameters.apply {
                            standardParameters()
                            append(NetworkConstants.FIELD_PAGE, page.toString())
                        }
                    }
                }.body()
            }
        }

        override suspend fun trendingMedia(): Result<PagedMediaResponse> {
            val endpoint = NetworkConstants.BASE_URL_TMDB + "/movie/upcoming"
            return this.suspendRunReThrowable("Failed to fetch media [endpoint=$endpoint]") {
                httpClient.get(endpoint) {
                    url { parameters.standardParameters() }
                }.body()
            }
        }

        override suspend fun tvDetails(id: Int): Result<MediaResponse> {
            return this.suspendRunReThrowable("Failed to fetch tv details [id=$id]") {
                httpClient.get(NetworkConstants.BASE_URL_TMDB + "/tv/$id") {
                    url { parameters.standardParameters() }
                }.body()
            }
        }

        override suspend fun recommendationsForTv(id: Int, page: Int): Result<PagedMediaResponse> {
            val endpoint = NetworkConstants.BASE_URL_TMDB + "/tv/$id/recommendations"
            return this.suspendRunReThrowable("Failed to fetch tv shows [id=$id, page=$page, endpoint=$endpoint]") {
                httpClient.get(endpoint) {
                    url {
                        parameters.apply {
                            standardParameters()
                            append(NetworkConstants.FIELD_PAGE, page.toString())
                        }
                    }
                }.body()
            }
        }

        override suspend fun similarForTv(id: Int, page: Int): Result<PagedMediaResponse> {
            val endpoint = NetworkConstants.BASE_URL_TMDB + "/tv/$id/similar"
            return this.suspendRunReThrowable("Failed to fetch tv shows [id=$id, page=$page, endpoint=$endpoint]") {
                httpClient.get(endpoint) {
                    url {
                        parameters.apply {
                            standardParameters()
                            append(NetworkConstants.FIELD_PAGE, page.toString())
                        }
                    }
                }.body()
            }
        }

        override suspend fun tvAiringToday(page: Int): Result<PagedMediaResponse> {
            val endpoint = NetworkConstants.BASE_URL_TMDB + "/tv/airing_today"
            return this.suspendRunReThrowable("Failed to fetch tv shows [page=$page, endpoint=$endpoint]") {
                httpClient.get(endpoint) {
                    url {
                        parameters.apply {
                            standardParameters()
                            append(NetworkConstants.FIELD_PAGE, page.toString())
                        }
                    }
                }.body()
            }
        }

        override suspend fun topRatedTv(page: Int): Result<PagedMediaResponse> {
            val endpoint = NetworkConstants.BASE_URL_TMDB + "/tv/top_rated"
            return this.suspendRunReThrowable("Failed to fetch tv shows [page=$page, endpoint=$endpoint]") {
                httpClient.get(endpoint) {
                    url {
                        parameters.apply {
                            standardParameters()
                            append(NetworkConstants.FIELD_PAGE, page.toString())
                        }
                    }
                }.body()
            }
        }

        override suspend fun popularTv(page: Int): Result<PagedMediaResponse> {
            val endpoint = NetworkConstants.BASE_URL_TMDB + "/tv/popular"
            return this.suspendRunReThrowable("Failed to fetch tv shows [page=$page, endpoint=$endpoint]") {
                httpClient.get(endpoint) {
                    url {
                        parameters.apply {
                            standardParameters()
                            append(NetworkConstants.FIELD_PAGE, page.toString())
                        }
                    }
                }.body()
            }
        }

        override suspend fun tvOnAirComingWeek(page: Int): Result<PagedMediaResponse> {
            val endpoint = NetworkConstants.BASE_URL_TMDB + "/tv/on_the_air"
            return this.suspendRunReThrowable("Failed to fetch tv show [page=$page, endpoint=$endpoint]") {
                httpClient.get(endpoint) {
                    url {
                        parameters.apply {
                            standardParameters()
                            append(NetworkConstants.FIELD_PAGE, page.toString())
                        }
                    }
                }.body()
            }
        }

        override suspend fun discoverTv(genreId: Int, page: Int): Result<PagedMediaResponse> {
            val endpoint = NetworkConstants.BASE_URL_TMDB + "/discover/tv"
            return this.suspendRunReThrowable("Failed to fetch tv show [genreId=$genreId, page=$page, endpoint=$endpoint]") {
                httpClient.get(endpoint) {
                    url {
                        parameters.apply {
                            standardParameters()
                            append(NetworkConstants.FIELD_WITH_GENRES, genreId.toString())
                            append(NetworkConstants.FIELD_PAGE, page.toString())
                        }
                    }
                }.body()
            }
        }

        override suspend fun discoverMovie(genreId: Int, page: Int): Result<PagedMediaResponse> {
            val endpoint = NetworkConstants.BASE_URL_TMDB + "/discover/movie"
            return this.suspendRunReThrowable("Failed to fetch movies [genreId=$genreId, page=$page, endpoint=$endpoint]") {
                httpClient.get(endpoint) {
                    url {
                        parameters.apply {
                            standardParameters()
                            append(NetworkConstants.FIELD_WITH_GENRES, genreId.toString())
                            append(NetworkConstants.FIELD_PAGE, page.toString())
                        }
                    }
                }.body()
            }
        }
    }
}


