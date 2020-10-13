package com.jesusbadenas.goldenspearchallenge.data.api.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ArtistsResponse(
    @SerializedName("artists") val artists: ArtistsBodyResponse
)
