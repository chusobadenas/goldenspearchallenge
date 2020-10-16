package com.jesusbadenas.goldenspearchallenge.data.api.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BodyResponse(
    @SerializedName("href") val href: String,
    @SerializedName("items") val items: List<ItemResponse>
)
