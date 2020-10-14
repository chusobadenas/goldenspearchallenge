package com.jesusbadenas.goldenspearchallenge.data.api

import com.jesusbadenas.goldenspearchallenge.data.api.response.ArtistsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("/v1/search")
    suspend fun searchArtists(
        @Query("q") query: String,
        @Query("limit") limit: Int = DEFAULT_LIMIT,
        @Query("offset") offset: Int = DEFAULT_OFFSET,
        @Query("type") type: String = ARTIST_TYPE
    ): ArtistsResponse

    companion object {
        private const val ARTIST_TYPE = "artist"
        const val BASE_URL = "https://api.spotify.com/"
        private const val DEFAULT_LIMIT = 20
        private const val DEFAULT_OFFSET = 0
    }
}
