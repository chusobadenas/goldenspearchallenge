package com.jesusbadenas.goldenspearchallenge.data.util

import com.jesusbadenas.goldenspearchallenge.data.api.response.ItemResponse
import com.jesusbadenas.goldenspearchallenge.data.model.Album
import com.jesusbadenas.goldenspearchallenge.data.model.Artist
import com.jesusbadenas.goldenspearchallenge.data.model.Track

fun ItemResponse.toAlbum(): Album = Album(
    id = id,
    name = name,
    imageUrl = images?.firstOrNull()?.url
)

fun ItemResponse.toArtist(): Artist = Artist(
    id = id,
    name = name,
    imageUrl = images?.firstOrNull()?.url
)

fun ItemResponse.toTrack(): Track = Track(
    id = id,
    name = name,
    duration = durationMs
)
