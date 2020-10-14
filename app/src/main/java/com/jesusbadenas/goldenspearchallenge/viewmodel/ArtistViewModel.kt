package com.jesusbadenas.goldenspearchallenge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesusbadenas.goldenspearchallenge.data.entities.Artist
import com.jesusbadenas.goldenspearchallenge.domain.repositories.SearchRepository
import kotlinx.coroutines.launch

class ArtistViewModel(private val searchRepository: SearchRepository) : ViewModel() {

    val artists = MutableLiveData<List<Artist>>()
    val emptyTextVisible = Transformations.map(artists) { list -> list.isEmpty() }

    fun searchArtists(query: String) {
        viewModelScope.launch {
            artists.value = searchRepository.getArtists(query)
        }
    }
}
