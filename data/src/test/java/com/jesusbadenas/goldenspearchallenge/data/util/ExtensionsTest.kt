package com.jesusbadenas.goldenspearchallenge.data.util

import com.jesusbadenas.goldenspearchallenge.data.api.response.ImageResponse
import com.jesusbadenas.goldenspearchallenge.data.api.response.ItemResponse
import com.jesusbadenas.goldenspearchallenge.data.model.Album
import com.jesusbadenas.goldenspearchallenge.data.model.Artist
import com.jesusbadenas.goldenspearchallenge.data.model.Track
import org.junit.Assert
import org.junit.Test

class ExtensionsTest {

    private val itemResponse = ItemResponse(
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

    @Test
    fun testAlbumConversionSuccess() {
        val item = itemResponse.copy(name = "Unplugged", type = "album")
        val result = item.toAlbum()
        val expected = Album(
            id = "1",
            name = "Unplugged",
            imageUrl = "https://i.scdn.co/image/1",
            tracks = mutableListOf()
        )

        Assert.assertEquals(expected, result)
    }

    @Test
    fun testArtistConversionSuccess() {
        val item = itemResponse.copy(name = "Nirvana", type = "artist")
        val result = item.toArtist()
        val expected = Artist(
            id = "1",
            name = "Nirvana",
            imageUrl = "https://i.scdn.co/image/1",
            albums = mutableListOf()
        )

        Assert.assertEquals(expected, result)
    }

    @Test
    fun testTrackConversionSuccess() {
        val item = itemResponse.copy(name = "Lithium", type = "track", durationMs = 150)
        val result = item.toTrack()
        val expected = Track(
            id = "1",
            name = "Lithium",
            duration = 150
        )

        Assert.assertEquals(expected, result)
    }
}
