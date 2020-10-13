package com.jesusbadenas.goldenspearchallenge.data.api

import com.jesusbadenas.goldenspearchallenge.data.api.request.ClientCredentials
import com.jesusbadenas.goldenspearchallenge.data.api.response.ApiTokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthorizationService {

    @POST("/api/token")
    suspend fun getApiToken(@Body credentials: ClientCredentials): ApiTokenResponse

    companion object {
        const val BASE_URL = "https://accounts.spotify.com/"
    }
}
