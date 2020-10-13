package com.jesusbadenas.goldenspearchallenge.domain.repositories

import com.jesusbadenas.goldenspearchallenge.data.api.APIService
import com.jesusbadenas.goldenspearchallenge.data.entities.Artist
import com.jesusbadenas.goldenspearchallenge.data.util.toArtist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchRepository(private val apiService: APIService) {

    suspend fun getArtists(name: String): List<Artist> =
        withContext(Dispatchers.IO) {
            apiService.searchArtists(name).artists.items.map { artistResponse ->
                artistResponse.toArtist()
            }
        }
}
