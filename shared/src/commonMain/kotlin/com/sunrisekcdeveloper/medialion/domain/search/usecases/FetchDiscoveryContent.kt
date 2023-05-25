package com.sunrisekcdeveloper.medialion.domain.search.usecases

import com.sunrisekcdeveloper.medialion.MediaItemUI
import com.sunrisekcdeveloper.medialion.TitledMedia
import com.sunrisekcdeveloper.medialion.domain.entities.TVShow
import com.sunrisekcdeveloper.medialion.domain.value.ID
import com.sunrisekcdeveloper.medialion.mappers.ListMapper
import com.sunrisekcdeveloper.medialion.repos.TVRepository
import io.github.aakira.napier.log
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList

interface FetchDiscoveryContent {
    suspend operator fun invoke(): List<TitledMedia>
    class Default(
        private val tvRepository: TVRepository,
        private val mapper: ListMapper<TVShow, MediaItemUI>
    ) : FetchDiscoveryContent {

        private val genresId: List<Pair<ID, String>> = listOf(
            ID(10759) to "Action & Adventure",
            ID(16) to "Animation",
            ID(99) to "Documentary",
            ID(18) to "Drama",
            ID(10751) to "Family",
            ID(10762) to "Kids",
            ID(9648) to "Mystery",
            ID(10764) to "Reality",
            ID(10765) to "Sci-Fi & Fantasy",
            ID(37) to "Western",
        )

        override suspend fun invoke(): List<TitledMedia> {
            val content = mutableListOf<TitledMedia>()
            genresId.shuffled()
                .take(6)
                .forEach { (genreId, title) ->
                val tvShows = tvRepository
                    .withGenre(genreId)
                    .onEach { log { "deadpool - ${it.id}" } }
                    .take(20)
                    .toList()
                TitledMedia(
                    title = title,
                    content = mapper.map(tvShows)
                ).also {
                    content.add(it)
                }
            }
            return content
        }
    }
}