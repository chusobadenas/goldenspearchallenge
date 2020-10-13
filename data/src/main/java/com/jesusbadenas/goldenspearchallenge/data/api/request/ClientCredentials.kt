package com.jesusbadenas.goldenspearchallenge.data.api.request

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ClientCredentials(
    @SerializedName("grant_type") val grantType: String = "client_credentials"
)
