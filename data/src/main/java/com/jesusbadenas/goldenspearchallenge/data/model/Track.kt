package com.jesusbadenas.goldenspearchallenge.data.model

class Track(
    id: String,
    name: String,
    val duration: Long? = null
) : Item(id = id, name = name, type = ItemType.TRACK)
