package com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.repo

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.sunrisekcdeveloper.medialion.domain.value.Title
import com.sunrisekcdeveloper.medialion.mappers.Mapper
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models.MediaRequirements
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models.MediaTypeNew
import com.sunrisekcdeveloper.medialion.newarch.components.shared.data.singleMedia.SingleMediaLocalDataSource
import com.sunrisekcdeveloper.medialion.newarch.components.shared.data.singleMedia.SingleMediaNetworkDto
import com.sunrisekcdeveloper.medialion.newarch.components.shared.data.singleMedia.SingleMediaRemoteDataSource
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.SingleMediaItem
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.repos.SingleMediaItemRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class SingleMediaItemRepositoryTest {

    private lateinit var singleMediaItemRepository: SingleMediaItemRepository
    private lateinit var remoteDataSource: SingleMediaRemoteDataSource.Fake
    private lateinit var localDataSource: SingleMediaLocalDataSource.Fake
    private val mediaRequirements = MediaRequirements(
        withTitle = Title(value = ""),
        withMediaType = MediaTypeNew.All,
        withCategories = listOf(),
        withText = "",
        withoutMedia = listOf(),
        amountOfMedia = 40
    )


    @BeforeTest
    fun setup() {
        remoteDataSource = SingleMediaRemoteDataSource.Fake()
        localDataSource = SingleMediaLocalDataSource.Fake()
        singleMediaItemRepository = SingleMediaItemRepository.D(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            dtoMapper = object : Mapper<SingleMediaNetworkDto, SingleMediaItem> {
                override fun map(input: SingleMediaNetworkDto): SingleMediaItem {
                    return when (input) {
                        is SingleMediaNetworkDto.MovieNetworkDto -> SingleMediaItem.Movie(name = input.title)
                        is SingleMediaNetworkDto.TVShow -> SingleMediaItem.TVShow(name = input.title)
                    }
                }
            }
        )
    }

    @Test
    fun `return a list of media matching supplied requirements`() = runTest {
        val media = singleMediaItemRepository.media(mediaRequirements)
        assertThat(media.size).isEqualTo(mediaRequirements.amountOfMedia)
    }

    @Test
    fun `throw an exception when remote datasource throws an exception`() = runTest {
        remoteDataSource.forceFailure = true
        assertFailsWith<Exception> { singleMediaItemRepository.media(mediaRequirements) }
    }

    @Test
    fun `do not throw an exception when an error occurs when calling local datasource`() = runTest {
        localDataSource.forceFailure = true
        val media = singleMediaItemRepository.media(mediaRequirements)
        assertThat(media.size).isEqualTo(mediaRequirements.amountOfMedia)
    }

}