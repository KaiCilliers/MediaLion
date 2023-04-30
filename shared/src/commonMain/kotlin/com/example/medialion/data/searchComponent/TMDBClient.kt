package com.example.medialion.data.searchComponent

import com.example.medialion.data.NetworkConstants
import com.example.medialion.data.extensions.safeApiCall
import com.example.medialion.data.extensions.standardParameters
import com.example.medialion.data.models.EpisodeResponse
import com.example.medialion.data.models.GenreListResponse
import com.example.medialion.data.models.KeywordResponse
import com.example.medialion.data.models.MovieDetailResponse
import com.example.medialion.data.models.PagedKeywordResults
import com.example.medialion.data.models.PagedMediaIdResponse
import com.example.medialion.data.models.PagedMovieResults
import com.example.medialion.data.models.PagedMultiResults
import com.example.medialion.data.models.PagedPersonResults
import com.example.medialion.data.models.PagedTVShowResults
import com.example.medialion.data.models.SeasonResponse
import com.example.medialion.data.models.TVDetailResponse
import com.example.medialion.domain.models.ResultOf
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineDispatcher

interface TMDBClient {

    // region search
    suspend fun multiSearch(query: String): ResultOf<PagedMultiResults>
    suspend fun searchTvShows(query: String): ResultOf<PagedTVShowResults>
    suspend fun searchPersons(query: String): ResultOf<PagedPersonResults>
    suspend fun searchMovies(query: String): ResultOf<PagedMovieResults>
    suspend fun searchKeywords(query: String): ResultOf<PagedKeywordResults>
    //endregion

    // region genre
    suspend fun movieGenres(): ResultOf<GenreListResponse>
    suspend fun tvGenres(): ResultOf<GenreListResponse>
    // endregion

    // region keyword
    suspend fun keywordNameForId(id: Int): ResultOf<KeywordResponse>
    // endregion

    // region movie
    suspend fun movieDetails(id: Int): MovieDetailResponse
    suspend fun movieKeywords(id: Int): KeywordResponse
    suspend fun recommendationsForMovie(id: Int, page: Int): PagedMovieResults
    suspend fun similarForMovie(id: Int, page: Int): PagedMovieResults
    suspend fun moviesNowInTheatres(page: Int): PagedMovieResults
    suspend fun topRatedMovies(page: Int): PagedMovieResults
    suspend fun popularMovies(page: Int): PagedMovieResults
    suspend fun moviesComingToTheatres(page: Int): PagedMovieResults
    // endregion

    // region trending
    suspend fun trendingMedia(): ResultOf<PagedMultiResults>
    // endregion

    // region tv
    suspend fun tvDetails(id: Int): ResultOf<TVDetailResponse>
    suspend fun tvKeywords(id: Int): ResultOf<KeywordResponse>
    suspend fun recommendationsForTv(id: Int): ResultOf<PagedTVShowResults>
    suspend fun similarForTv(id: Int): ResultOf<PagedTVShowResults>
    suspend fun tvAiringToday(): ResultOf<PagedTVShowResults>
    suspend fun topRatedTv(): ResultOf<PagedTVShowResults>
    suspend fun popularTv(): ResultOf<PagedTVShowResults>
    suspend fun tvOnAirComingWeek(): ResultOf<PagedTVShowResults>
    // endregion

    // region season
    suspend fun seasonDetails(tvId: Int, seasonNumber: Int): ResultOf<SeasonResponse>
    // endregion

    // region episode
    suspend fun episodeDetails(tvId: Int, seasonNumber: Int, episodeNumber: Int): ResultOf<EpisodeResponse>
    // endregion

    // region changes
    suspend fun latestMovieChanges(): ResultOf<PagedMediaIdResponse>
    suspend fun latestTvChanges(): ResultOf<PagedMediaIdResponse>
    // endregion

    class Default(
        private val httpClient: HttpClient,
        private val dispatcher: CoroutineDispatcher,
    ) : TMDBClient {

        // region search
        override suspend fun searchMovies(query: String): ResultOf<PagedMovieResults> = dispatcher.safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/search/movie") {
                    url {
                        parameters.apply {
                            standardParameters()
                            append(NetworkConstants.FIELD_QUERY, query)
                            append(NetworkConstants.FIELD_PAGE, "1")
                        }
                    }
        }.body()
        }

        override suspend fun multiSearch(query: String): ResultOf<PagedMultiResults> = dispatcher.safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/search/multi") {
                    url {
                        parameters.apply {
                            standardParameters()
                            append(NetworkConstants.FIELD_PAGE, "1")
                            append(NetworkConstants.FIELD_QUERY, query)
                        }
                    }
                }.body()
        }

        // todo refer to tvshows as TV
        override suspend fun searchTvShows(query: String): ResultOf<PagedTVShowResults> = dispatcher.safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/search/tv") {
                url {
                    parameters.apply {
                        standardParameters()
                        append(NetworkConstants.FIELD_PAGE, "1")
                        append(NetworkConstants.FIELD_QUERY, query)
                    }
                }
            }.body()
        }

        override suspend fun searchPersons(query: String): ResultOf<PagedPersonResults> = dispatcher.safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/search/person") {
                url {
                    parameters.apply {
                        standardParameters()
                        append(NetworkConstants.FIELD_PAGE, "1")
                        append(NetworkConstants.FIELD_QUERY, query)
                    }
                }
            }.body()
        }

        override suspend fun searchKeywords(query: String): ResultOf<PagedKeywordResults>  = dispatcher.safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/search/keyword").body()
        }

        // endregion

        // region genre
        override suspend fun movieGenres(): ResultOf<GenreListResponse> = dispatcher.safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/genre/movie/list") {
                url { parameters.standardParameters() }
            }.body()
        }

        override suspend fun tvGenres(): ResultOf<GenreListResponse> = dispatcher.safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/genre/tv/list") {
                url {
                    parameters.apply { standardParameters() }
                }
            }.body()
        }
        // endregion

        // region keyword
        override suspend fun keywordNameForId(id: Int): ResultOf<KeywordResponse> = dispatcher.safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/keyword/$id") {
                url { parameters.standardParameters() }
            }
        }
        // endregion

        // region movie
        override suspend fun movieDetails(id: Int): MovieDetailResponse = safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/movie/$id"){
                url { parameters.standardParameters() }
            }
        }

        override suspend fun movieKeywords(id: Int): KeywordResponse = safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/movie/$id/keywords"){
                url { parameters.standardParameters() }
            }
        }

        override suspend fun recommendationsForMovie(id: Int, page: Int): PagedMovieResults = safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/movie/$id/recommendations"){
                url { parameters.apply {
                    standardParameters()
                    append(NetworkConstants.FIELD_PAGE, page.toString())
                } }
            }
        }

        override suspend fun similarForMovie(id: Int, page: Int): PagedMovieResults = safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/movie/$id/similar"){
                url { parameters.apply {
                    standardParameters()
                    append(NetworkConstants.FIELD_PAGE, page.toString())
                } }
            }
        }

        override suspend fun moviesNowInTheatres(page: Int): PagedMovieResults = safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/movie/now_playing"){
                url { parameters.apply {
                    standardParameters()
                    append(NetworkConstants.FIELD_PAGE, page.toString())
                } }
            }
        }

        override suspend fun topRatedMovies(page: Int): PagedMovieResults = safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/movie/top_rated"){
                url { parameters.apply {
                    standardParameters()
                    append(NetworkConstants.FIELD_PAGE, page.toString())
                } }
            }
        }

        override suspend fun popularMovies(page: Int): PagedMovieResults = safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/movie/popular"){
                url { parameters.apply {
                    standardParameters()
                    append(NetworkConstants.FIELD_PAGE, page.toString())
                } }
            }
        }

        override suspend fun moviesComingToTheatres(page: Int): PagedMovieResults = safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/movie/upcoming"){
                url { parameters.apply {
                    standardParameters()
                    append(NetworkConstants.FIELD_PAGE, page.toString())
                } }
            }
        }
        // endregion

        // region trending
        override suspend fun trendingMedia(): ResultOf<PagedMultiResults> = dispatcher.safeApiCall {
            // todo possible values ALL, MOVIE, TV and period DAY, WEEK
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/trending/all/day"){
                url { parameters.standardParameters() }
            }.body()
        }
        // endregion

        // region tv
        override suspend fun tvDetails(id: Int): ResultOf<TVDetailResponse> = dispatcher.safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/tv/$id"){
                url { parameters.standardParameters() }
            }.body()
        }

        override suspend fun tvKeywords(id: Int): ResultOf<KeywordResponse> = dispatcher.safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/tv/$id/keywords").body()
        }

        override suspend fun recommendationsForTv(id: Int): ResultOf<PagedTVShowResults> = dispatcher.safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/tv/$id/recommendations").body()
        }

        override suspend fun similarForTv(id: Int): ResultOf<PagedTVShowResults> = dispatcher.safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/tv/$id/similar").body()
        }

        override suspend fun tvAiringToday(): ResultOf<PagedTVShowResults> = dispatcher.safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/tv/airing_today").body()
        }

        override suspend fun topRatedTv(): ResultOf<PagedTVShowResults> = dispatcher.safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/tv/top_rated").body()
        }

        override suspend fun popularTv(): ResultOf<PagedTVShowResults> = dispatcher.safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/tv/popular").body()
        }

        override suspend fun tvOnAirComingWeek(): ResultOf<PagedTVShowResults> = dispatcher.safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/tv/on_the_air").body()
        }
        // endregion

        // region season
        override suspend fun seasonDetails(tvId: Int, seasonNumber: Int): ResultOf<SeasonResponse> = dispatcher.safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "tv/$tvId/season/$seasonNumber"){
                url { parameters.standardParameters() }
            }.body()
        }
        // endregion

        // region episode
        override suspend fun episodeDetails(tvId: Int, seasonNumber: Int, episodeNumber: Int): ResultOf<EpisodeResponse> = dispatcher.safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "tv/$tvId/season/$seasonNumber/episode/$episodeNumber"){
                url { parameters.standardParameters() }
            }.body()
        }
        // endregion

        // region changes
        override suspend fun latestMovieChanges(): ResultOf<PagedMediaIdResponse> = dispatcher.safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/movie/changes"){
                url { parameters.standardParameters() }
            }.body()
        }

        override suspend fun latestTvChanges(): ResultOf<PagedMediaIdResponse> = dispatcher.safeApiCall {
            httpClient.get(NetworkConstants.BASE_URL_TMDB + "/tv/changes"){
                url { parameters.standardParameters() }
            }.body()
        }
        // endregion

    }
}


