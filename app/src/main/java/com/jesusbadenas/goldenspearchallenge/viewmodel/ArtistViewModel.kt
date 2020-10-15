package com.jesusbadenas.goldenspearchallenge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesusbadenas.goldenspearchallenge.data.entities.Artist
import com.jesusbadenas.goldenspearchallenge.domain.repositories.SearchRepository
import com.jesusbadenas.goldenspearchallenge.util.addMoreItems
import kotlinx.coroutines.launch

class ArtistViewModel(private val searchRepository: SearchRepository) : ViewModel() {

    val artists = MutableLiveData<MutableList<Artist>>()
    val emptyTextVisible = Transformations.map(artists) { list -> list.isEmpty() }

    private var currentOffset = 0
    var isLoading = false

    private fun searchArtists(query: String) {
        isLoading = true
        viewModelScope.launch {
            val result = searchRepository.getArtists(query, currentOffset)
            artists.addMoreItems(result)
            isLoading = false
        }
    }

    fun loadArtists(query: String) {
        currentOffset = 0
        artists.value?.clear()
        searchArtists(query)
    }

    fun loadMoreArtists(query: String) {
        currentOffset += PAGE_SIZE
        searchArtists(query)
    }

    companion object {
        private const val PAGE_SIZE = 20
        const val MAX_SIZE = 2000
    }
}
