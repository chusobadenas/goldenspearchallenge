package com.jesusbadenas.goldenspearchallenge.data.api.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ApiTokenResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("expires_in") val expiresIn: Long
)
