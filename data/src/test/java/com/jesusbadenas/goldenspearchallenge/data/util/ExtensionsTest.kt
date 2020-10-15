package com.jesusbadenas.goldenspearchallenge.data.util

import com.jesusbadenas.goldenspearchallenge.data.api.response.ArtistResponse
import com.jesusbadenas.goldenspearchallenge.data.api.response.ImageResponse
import com.jesusbadenas.goldenspearchallenge.data.model.Artist
import org.junit.Assert
import org.junit.Test

class ExtensionsTest {

    @Test
    fun testArtistConversionSuccess() {
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

        val result = artistResponse.toArtist()
        val expected = Artist(
            id = "1",
            name = "John Doe",
            imageUrl = "https://i.scdn.co/image/1"
        )

        Assert.assertEquals(expected, result)
    }
}
