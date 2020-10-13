package com.jesusbadenas.goldenspearchallenge.data.util

import com.jesusbadenas.goldenspearchallenge.data.api.response.ArtistResponse
import com.jesusbadenas.goldenspearchallenge.data.entities.Artist

fun ArtistResponse.toArtist(): Artist = Artist(
    id = id,
    name = name,
    imageUrl = images[0].url
)
