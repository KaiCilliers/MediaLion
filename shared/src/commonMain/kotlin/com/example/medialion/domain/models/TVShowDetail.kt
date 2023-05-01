package com.example.medialion.domain.models

import com.example.medialion.data.models.EpisodeResponse
import com.example.medialion.data.models.SeasonShortResponse
import com.example.medialion.data.models.supporting.Genre
import com.example.medialion.data.models.supporting.SpokenLanguage

data class TVShowDetail(
    val adult: Boolean,
    val backdropPath: String,
    val episodeRunTime: List<Int>,
    val firstAirDate: String,
    val genres: List<Genre>,
    val id: Int,
    val inProduction: Boolean,
    val languages: List<String>,
    val lastAirDate: String?,
    val lastEpisodeToAir: EpisodeResponse?,
    val name: String,
    val nextEpisodeToAir: EpisodeResponse?,
    val numberOfEpisodes: Int,
    val numberOfSeasons: Int,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val seasons: List<SeasonShortResponse>,
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val type: String,
    val voteAverage: Double,
    val voteCount: Int,
)
