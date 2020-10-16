package com.jesusbadenas.goldenspearchallenge.domain.datasource

import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import com.jesusbadenas.goldenspearchallenge.data.api.APIService
import com.jesusbadenas.goldenspearchallenge.data.api.response.ArtistsResponse
import com.jesusbadenas.goldenspearchallenge.data.api.response.BodyResponse
import com.jesusbadenas.goldenspearchallenge.data.api.response.ImageResponse
import com.jesusbadenas.goldenspearchallenge.data.api.response.ItemResponse
import com.jesusbadenas.goldenspearchallenge.data.model.Album
import com.jesusbadenas.goldenspearchallenge.data.model.Artist
import com.jesusbadenas.goldenspearchallenge.data.model.Track
import com.jesusbadenas.goldenspearchallenge.test.CoroutinesTestRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

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
        dataSource = ArtistsDataSource(apiService, "nirvana")
    }

    @Test
    fun testGetArtistsSuccess() {
        val bodyResponse = BodyResponse(href = "", items = emptyList())
        val itemResponse = ItemResponse(
            id = "1",
            href = "",
            name = "",
            type = "",
            uri = "",
            images = listOf(
                ImageResponse(
                    height = 64,
                    width = 64,
                    url = "https://i.scdn.co/image/1"
                )
            )
        )

        val artistResponse = itemResponse.copy(name = "Nirvana", type = "artist")
        val artistsResponse = ArtistsResponse(bodyResponse.copy(items = listOf(artistResponse)))
        coEvery {
            apiService.searchArtists(query = "nirvana", limit = 6, offset = 0)
        } returns artistsResponse

        val albumResponse = itemResponse.copy(name = "Unplugged", type = "album")
        val albumsResponse = bodyResponse.copy(items = listOf(albumResponse))
        coEvery { apiService.getArtistAlbums(id = "1") } returns albumsResponse

        val trackResponse = itemResponse.copy(name = "Lithium", type = "track", durationMs = 150)
        val tracksResponse = bodyResponse.copy(items = listOf(trackResponse))
        coEvery { apiService.getAlbumTracks(id = "1") } returns tracksResponse

        val params = LoadParams.Refresh(key = 0, loadSize = 6, placeholdersEnabled = false)
        val result: LoadResult<Int, Artist> = runBlocking { dataSource.load(params) }
        val expected = Artist(
            id = "1",
            name = "Nirvana",
            imageUrl = "https://i.scdn.co/image/1",
            albums = mutableListOf(
                Album(
                    id = "1",
                    name = "Unplugged",
                    imageUrl = "https://i.scdn.co/image/1",
                    tracks = mutableListOf(Track(id = "1", name = "Lithium", duration = 150))
                )
            )
        )

        Assert.assertTrue(result is LoadResult.Page)
        val resultPage = result as LoadResult.Page
        Assert.assertEquals(expected, resultPage.data[0])
        Assert.assertEquals(1, resultPage.nextKey)
    }

    @Test
    fun testGetArtistsThrowsHttpException() {
        val exception = HttpException(Response.error<String>(500, "Error 500".toResponseBody()))
        coEvery {
            apiService.searchArtists(query = "nirvana", limit = 6, offset = 0)
        } throws exception

        val params = LoadParams.Refresh(key = 0, loadSize = 6, placeholdersEnabled = false)
        val result: LoadResult<Int, Artist> = runBlocking { dataSource.load(params) }

        Assert.assertTrue(result is LoadResult.Error)
        val resultError = result as LoadResult.Error
        Assert.assertEquals(exception.message, resultError.throwable.message)
    }
}
