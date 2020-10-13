package com.jesusbadenas.goldenspearchallenge.data.api

import com.jesusbadenas.goldenspearchallenge.data.api.response.ArtistsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("/v1/search")
    suspend fun searchArtist(
        @Query("q") query: String,
        @Query("type") type: String = "artist"
    ): ArtistsResponse

    companion object {
        const val BASE_URL = "https://api.spotify.com/"
    }
}
