package com.jesusbadenas.goldenspearchallenge.data.api

import com.jesusbadenas.goldenspearchallenge.data.api.response.ArtistsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("/v1/search")
    suspend fun searchArtists(
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("type") type: String = ARTIST_TYPE
    ): ArtistsResponse

    companion object {
        private const val ARTIST_TYPE = "artist"
        const val BASE_URL = "https://api.spotify.com/"
    }
}
