package com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.repo

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.sunrisekcdeveloper.medialion.mappers.Mapper
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models.MediaCategory
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models.MediaTypeNew
import com.sunrisekcdeveloper.medialion.newarch.components.shared.data.mediaCategory.MediaCategoryApiDto
import com.sunrisekcdeveloper.medialion.newarch.components.shared.data.mediaCategory.MediaCategoryEntityDto
import com.sunrisekcdeveloper.medialion.newarch.components.shared.data.mediaCategory.MediaCategoryLocalDataSource
import com.sunrisekcdeveloper.medialion.newarch.components.shared.data.mediaCategory.MediaCategoryRemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class MediaCategoryRepositoryTest {
    
    private lateinit var mediaCategoryRepository: MediaCategoryRepository
    private lateinit var localDataSource: MediaCategoryLocalDataSource.Fake
    private lateinit var remoteDataSource: MediaCategoryRemoteDataSource.Fake

    private val apiMapper = object : Mapper<MediaCategoryApiDto, MediaCategory> {
        override fun map(input: MediaCategoryApiDto): MediaCategory {
            val mediaType = when ((0..100).random() % 3) {
                0 -> MediaTypeNew.TVShow
                1 -> MediaTypeNew.Movie
                else -> MediaTypeNew.All
            }
            return MediaCategory.D(name = input.id, appliesToType = mediaType)
        }
    }
    private val entityMapper = object : Mapper<MediaCategoryEntityDto, MediaCategory> {
        override fun map(input: MediaCategoryEntityDto): MediaCategory {
            val mediaType = when (input.placeholder % 3) {
                0 -> MediaTypeNew.TVShow
                1 -> MediaTypeNew.Movie
                else -> MediaTypeNew.All
            }
            return MediaCategory.D(name = input.placeholder.toString(), appliesToType = mediaType)
        }
    }
    private val domainMapper = object : Mapper<MediaCategory, MediaCategoryEntityDto> {
        override fun map(input: MediaCategory): MediaCategoryEntityDto {
            return MediaCategoryEntityDto(placeholder = input.hashCode())
        }
    }
    
    @BeforeTest
    fun setup() {
        localDataSource = MediaCategoryLocalDataSource.Fake()
        remoteDataSource = MediaCategoryRemoteDataSource.Fake()
        mediaCategoryRepository = MediaCategoryRepository.D(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource,
            apiMapper = apiMapper,
            entityMapper = entityMapper,
            domainMapper = domainMapper,
        )
    }
    
    @Test
    fun `return all media categories when all function is invoked`() = runTest {
        val categories = mediaCategoryRepository.all()
        assertTrue(categories.isNotEmpty())
    }

    @Test
    fun `if local cache is empty then fetch media categories from remote datasource when all function is invoked`() = runTest {
        localDataSource.clearCache = true
        val categories = mediaCategoryRepository.all()
        assertTrue(categories.isNotEmpty())
    }

    @Test
    fun `return the exact amount of categories when random categories are requested`() = runTest {
        val categories = mediaCategoryRepository.getRandomOrAll(2)
        assertThat(categories.size).isEqualTo(2)
    }
    
    @Test
    fun `if there is not enough categories to meet random requirement then return all the categories`() = runTest {
        val categories = mediaCategoryRepository.getRandomOrAll(100)
        assertTrue(categories.isNotEmpty())
    }

    @Test
    fun `return only categories matching the defined media type when random categories are requested`() = runTest {
        val categories = mediaCategoryRepository.getRandomOrAll(amount = 2, mediaType = MediaTypeNew.Movie)
        assertTrue(categories.isNotEmpty())
        assertTrue(categories.all { it.appliesToType(MediaTypeNew.Movie) })
    }

    @Test
    fun `throw an exception when local cache is empty and remote datasource has an exception`() = runTest {
        localDataSource.clearCache = true
        remoteDataSource.forceFailure = true

        assertFailsWith<Exception> { mediaCategoryRepository.all() }
    }

    @Test
    fun `throw an exception when when local cache has an exception`() = runTest {
        localDataSource.forceFailure = true
        assertFailsWith<Exception> { mediaCategoryRepository.all() }
    }
}