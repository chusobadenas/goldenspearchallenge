package com.jesusbadenas.goldenspearchallenge.data.api.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ImageResponse(
    @SerializedName("height") val height: Int,
    @SerializedName("width") val width: Int,
    @SerializedName("url") val url: String
)
