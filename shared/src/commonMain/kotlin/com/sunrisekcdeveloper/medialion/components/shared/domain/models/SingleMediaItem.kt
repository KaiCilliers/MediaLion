package com.sunrisekcdeveloper.medialion.components.shared.domain.models

import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaCategory
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Genre
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Overview
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title

sealed interface SingleMediaItem {
    fun name(): Title
    fun identifier(): ID

    sealed class D(
        open val id: ID,
        open val title: Title,
        open val backdropPath: String, // todo value object to wrap primitive
        open val posterPath: String,
        open val categories: List<MediaCategory>,
        open val popularity: Double, // todo value object to wrap these rating values,
        open val voteAverage: Double,
        open val adult: Boolean, // todo remove this field
        open val overview: Overview,
        open val voteCount: Int,
        ) : SingleMediaItem {
        override fun name(): Title {
            return title
        }

        override fun identifier(): ID {
            return id
        }
    }
    data class Movie(
        override val id: ID,
        override val title: Title,
        override val backdropPath: String,
        override val posterPath: String,
        override val categories: List<MediaCategory>,
        override val popularity: Double,
        override val voteAverage: Double,
        override val adult: Boolean,
        override val overview: Overview,
        override val voteCount: Int,
        val releaseDate: String, // todo use a better type
    ) : SingleMediaItem.D(id, title, backdropPath, posterPath, categories, popularity, voteAverage, adult, overview, voteCount), SingleMediaItem {
        constructor(name: String) : this(id = ID.Def(), name = name)
        constructor(id: ID, name: String) : this(id = id, title = Title(name))
        constructor(id: ID, title: Title) : this(
            id = id,
            title = title,
            backdropPath = "",
            posterPath = "",
            categories = listOf(),
            popularity = 0.0,
            voteAverage = 0.0,
            adult = true,
            releaseDate = "",
            overview = Overview(""),
            voteCount = 0,
        )
        override fun equals(other: Any?): Boolean {
            return when {
                other == null || other !is SingleMediaItem.Movie -> false
                other.identifier().uniqueIdentifier() == id.uniqueIdentifier() -> true
                else -> false
            }
        }

        override fun hashCode(): Int {
            return id.uniqueIdentifier().hashCode()
        }
    }

    data class TVShow(
        override val id: ID,
        override val title: Title,
        override val backdropPath: String,
        override val posterPath: String,
        override val categories: List<MediaCategory>,
        override val popularity: Double,
        override val voteAverage: Double,
        override val adult: Boolean,
        override val overview: Overview,
        override val voteCount: Int,
        val firstAirDate: String, // todo use better typing
    ) : SingleMediaItem.D(id, title, backdropPath, posterPath, categories, popularity, voteAverage, adult, overview, voteCount), SingleMediaItem {
        constructor(name: String) : this(id = ID.Def(), name = name)
        constructor(id: ID, name: String) : this(id = id, title = Title(name))
        constructor(id: ID, title: Title) : this(
            id = id,
            title = title,
            backdropPath = "",
            posterPath = "",
            categories = listOf(),
            popularity = 0.0,
            voteAverage = 0.0,
            adult = true,
            firstAirDate = "",
            overview = Overview(""),
            voteCount = 0,
        )

        override fun equals(other: Any?): Boolean {
            return when {
                other == null || other !is SingleMediaItem.TVShow -> false
                other.identifier().uniqueIdentifier() == id.uniqueIdentifier() -> true
                else -> false
            }
        }

        override fun hashCode(): Int {
            return id.uniqueIdentifier().hashCode()
        }
    }
}