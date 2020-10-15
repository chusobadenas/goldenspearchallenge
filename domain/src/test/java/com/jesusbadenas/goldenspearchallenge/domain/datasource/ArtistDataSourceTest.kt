package com.jesusbadenas.goldenspearchallenge.domain.datasource

import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
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
class ArtistDataSourceTest {

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    @MockK
    private lateinit var apiService: APIService

    private lateinit var dataSource: ArtistsDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = ArtistsDataSource(apiService, "john")
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
        coEvery {
            apiService.searchArtists(query = "john", limit = 20, offset = 0)
        } returns response

        val params = LoadParams.Refresh(key = 0, loadSize = 20, placeholdersEnabled = false)
        val result: LoadResult<Int, Artist> = runBlocking { dataSource.load(params) }
        val expected = Artist(
            id = "1",
            name = "John Doe",
            imageUrl = "https://i.scdn.co/image/1"
        )

        Assert.assertTrue(result is LoadResult.Page)
        val resultPage = result as LoadResult.Page
        Assert.assertEquals(expected, resultPage.data[0])
        Assert.assertEquals(1, resultPage.nextKey)
    }

    @Test
    fun testGetArtistsThrowsException() {
        val exception = Exception("Error 500")
        coEvery {
            apiService.searchArtists(query = "john", limit = 20, offset = 0)
        } throws exception

        val params = LoadParams.Refresh(key = 0, loadSize = 20, placeholdersEnabled = false)
        val result: LoadResult<Int, Artist> = runBlocking { dataSource.load(params) }

        Assert.assertTrue(result is LoadResult.Error)
        val resultError = result as LoadResult.Error
        Assert.assertEquals(exception.message, resultError.throwable.message)
    }
}
