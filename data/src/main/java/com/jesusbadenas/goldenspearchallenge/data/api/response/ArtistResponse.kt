package com.jesusbadenas.goldenspearchallenge.data.api.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ArtistResponse(
    @SerializedName("id") val id: String,
    @SerializedName("href") val href: String,
    @SerializedName("images") val images: List<ImageResponse>,
    @SerializedName("name") val name: String,
    @SerializedName("popularity") val popularity: Int,
    @SerializedName("type") val type: String,
    @SerializedName("uri") val uri: String
)
