package com.jesusbadenas.goldenspearchallenge.data.model

class Album(
    id: String,
    name: String,
    val imageUrl: String? = null,
    val tracks: MutableList<Track> = mutableListOf()
) : Item(id = id, name = name, type = ItemType.ALBUM)
