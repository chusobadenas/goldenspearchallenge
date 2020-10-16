package com.jesusbadenas.goldenspearchallenge.data.api

import com.jesusbadenas.goldenspearchallenge.data.api.response.ArtistsResponse
import com.jesusbadenas.goldenspearchallenge.data.api.response.BodyResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET("/v1/search")
    suspend fun searchArtists(
        @Query("q") query: String,
        @Query(LIMIT_PARAM) limit: Int,
        @Query(OFFSET_PARAM) offset: Int,
        @Query("type") type: String = ARTIST_TYPE
    ): ArtistsResponse

    @GET("/v1/artists/{id}/albums")
    suspend fun getArtistAlbums(
        @Path(ID_PARAM) id: String,
        @Query(LIMIT_PARAM) limit: Int = ALBUMS_MAX_LIMIT,
        @Query("include_groups") includeGroups: String = ALBUM_GROUP
    ): BodyResponse

    @GET("/v1/albums/{id}/tracks")
    suspend fun getAlbumTracks(
        @Path(ID_PARAM) id: String,
        @Query(LIMIT_PARAM) limit: Int = TRACKS_MAX_LIMIT
    ): BodyResponse

    companion object {
        private const val ALBUM_GROUP = "album"
        private const val ALBUMS_MAX_LIMIT = 18
        private const val ARTIST_TYPE = "artist"
        const val BASE_URL = "https://api.spotify.com/"
        private const val ID_PARAM = "id"
        private const val LIMIT_PARAM = "limit"
        private const val OFFSET_PARAM = "offset"
        private const val TRACKS_MAX_LIMIT = 30
    }
}
