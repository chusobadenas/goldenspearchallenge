package com.jesusbadenas.goldenspearchallenge.data.api.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ItemResponse(
    @SerializedName("id") val id: String,
    @SerializedName("duration_ms") val durationMs: Long? = null,
    @SerializedName("href") val href: String,
    @SerializedName("images") val images: List<ImageResponse>? = null,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("uri") val uri: String
)
