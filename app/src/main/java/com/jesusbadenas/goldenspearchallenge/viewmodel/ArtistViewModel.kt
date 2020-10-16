package com.jesusbadenas.goldenspearchallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.jesusbadenas.goldenspearchallenge.data.model.Artist
import com.jesusbadenas.goldenspearchallenge.domain.repositories.SearchRepository

class ArtistViewModel(private val searchRepository: SearchRepository) : BaseViewModel() {

    private val queryLiveData = MutableLiveData<String>()
    val artists: LiveData<PagingData<Artist>> = Transformations.switchMap(queryLiveData) { query ->
        searchRepository.searchArtists(query, viewModelScope)
    }

    val emptyTextVisible = MutableLiveData(true)
    val loadingVisible = MutableLiveData(false)

    fun searchArtists(query: String) {
        queryLiveData.value = query
    }
}
