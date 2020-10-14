package com.jesusbadenas.goldenspearchallenge.data.api

import com.jesusbadenas.goldenspearchallenge.data.api.response.ApiTokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthorizationService {

    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("/api/token")
    suspend fun getApiToken(@Field(GRANT_TYPE) grantType: String = CLIENT_CREDENTIALS): ApiTokenResponse

    companion object {
        const val BASE_URL = "https://accounts.spotify.com/"
        private const val CLIENT_CREDENTIALS = "client_credentials"
        private const val GRANT_TYPE = "grant_type"
    }
}
