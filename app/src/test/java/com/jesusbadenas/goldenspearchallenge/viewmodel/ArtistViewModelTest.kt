package com.jesusbadenas.goldenspearchallenge.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jesusbadenas.goldenspearchallenge.data.entities.Artist
import com.jesusbadenas.goldenspearchallenge.domain.repositories.SearchRepository
import com.jesusbadenas.goldenspearchallenge.test.CoroutinesTestRule
import com.jesusbadenas.goldenspearchallenge.test.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtistViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    @MockK
    private lateinit var searchRepository: SearchRepository

    private lateinit var viewModel: ArtistViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = ArtistViewModel(searchRepository)
    }

    @Test
    fun testLoadArtistsSuccess() = coroutineRule.runBlockingTest {
        val artist = Artist(id = "1", name = "John Doe", imageUrl = "https://i.scdn.co/image/1")
        coEvery { searchRepository.getArtists("john", 0) } returns listOf(artist)

        viewModel.loadArtists("john")
        val result = viewModel.artists.getOrAwaitValue()

        coVerify { searchRepository.getArtists("john", 0) }
        Assert.assertNotNull(result)
        Assert.assertEquals(artist, result[0])
    }

    @Test
    fun testLoadMoreArtistsSuccess() = coroutineRule.runBlockingTest {
        val artist = Artist(id = "1", name = "John Doe", imageUrl = "https://i.scdn.co/image/1")
        coEvery { searchRepository.getArtists("john", 20) } returns listOf(artist)

        viewModel.loadMoreArtists("john")
        val result = viewModel.artists.getOrAwaitValue()

        coVerify { searchRepository.getArtists("john", 20) }
        Assert.assertNotNull(result)
        Assert.assertEquals(artist, result[0])
    }
}
