package com.jesusbadenas.goldenspearchallenge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesusbadenas.goldenspearchallenge.data.entities.Artist
import com.jesusbadenas.goldenspearchallenge.domain.repositories.SearchRepository
import kotlinx.coroutines.launch

class ArtistViewModel(private val searchRepository: SearchRepository) : ViewModel() {

    val artists = MutableLiveData<List<Artist>>()

    fun searchArtists(name: String) {
        viewModelScope.launch {
            artists.value = searchRepository.getArtists(name)
        }
    }
}
