package com.jesusbadenas.goldenspearchallenge.domain.repositories

import com.jesusbadenas.goldenspearchallenge.data.api.APIService
import com.jesusbadenas.goldenspearchallenge.data.api.response.ArtistResponse
import com.jesusbadenas.goldenspearchallenge.data.api.response.ArtistsBodyResponse
import com.jesusbadenas.goldenspearchallenge.data.api.response.ArtistsResponse
import com.jesusbadenas.goldenspearchallenge.data.api.response.ImageResponse
import com.jesusbadenas.goldenspearchallenge.data.entities.Artist
import com.jesusbadenas.goldenspearchallenge.test.CoroutinesTestRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchRepositoryTest {

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    @MockK
    private lateinit var apiService: APIService

    private lateinit var searchRepository: SearchRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        searchRepository = SearchRepository(apiService)
    }

    @Test
    fun testGetArtistsSuccess() {
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
        coEvery { apiService.searchArtists("john") } returns response

        val artists = runBlocking { searchRepository.getArtists("john", 0) }
        val expected = Artist(
            id = "1",
            name = "John Doe",
            imageUrl = "https://i.scdn.co/image/1"
        )

        Assert.assertNotNull(artists)
        Assert.assertEquals(expected, artists[0])
    }
}
