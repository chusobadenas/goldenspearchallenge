package com.jesusbadenas.goldenspearchallenge.data.model

open class Item(
    val id: String,
    val name: String,
    val type: ItemType
) {
    enum class ItemType {
        ARTIST, ALBUM, TRACK
    }
}
