package com.jesusbadenas.goldenspearchallenge.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jesusbadenas.goldenspearchallenge.data.api.APIService
import com.jesusbadenas.goldenspearchallenge.data.api.response.ArtistResponse
import com.jesusbadenas.goldenspearchallenge.data.api.response.ArtistsBodyResponse
import com.jesusbadenas.goldenspearchallenge.data.api.response.ArtistsResponse
import com.jesusbadenas.goldenspearchallenge.data.api.response.ImageResponse
import com.jesusbadenas.goldenspearchallenge.test.CoroutinesTestRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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
    private lateinit var apiService: APIService

    private lateinit var viewModel: ArtistViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = ArtistViewModel(apiService)
    }

    @Test
    fun testLoadArtistsSuccess() {
        val artistResponse = ArtistResponse(
            id = "1",
            href = "https://api.spotify.com/v1/artists/1",
            name = "John Doe",
            popularity = 0,
            type = "artist",
            uri = "spotify:artist:1",
            images = listOf(
                ImageResponse(
                    height = 64,
                    width = 64,
                    url = "https://i.scdn.co/image/1"
                )
            )
        )
        val response = ArtistsResponse(
            ArtistsBodyResponse(
                href = "https://api.spotify.com/v1/artists/1",
                items = listOf(artistResponse)
            )
        )
        coEvery {
            apiService.searchArtists(query = "john", limit = 20, offset = 0)
        } returns response

        val resultFlow = viewModel.searchArtists("john")

        coroutineRule.runBlockingTest {
            Assert.assertNotNull(resultFlow.first())
        }
    }
}
