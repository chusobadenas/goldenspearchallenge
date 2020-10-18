package com.jesusbadenas.goldenspearchallenge.data.model

class Artist(
    id: String,
    name: String,
    val imageUrl: String? = null,
    val albums: MutableList<Album> = mutableListOf()
) : Item(id = id, name = name, type = ItemType.ARTIST)
