package com.example.medialion.domain.components.search.wip.domain

import com.example.medialion.data.models.supporting.Genre
import com.example.medialion.data.models.supporting.ProductionCompany
import com.example.medialion.data.models.supporting.ProductionCountry
import com.example.medialion.data.models.supporting.SpokenLanguage
import kotlinx.serialization.SerialName

data class MovieDetail(
    val adult: Boolean,
    val backdropPath: String,
    val budget: Int,
    val genres: List<Genre>,
    val id: Int,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String?,
    val revenue: Int,
    val runtime: Int,
    val tagline: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
)
