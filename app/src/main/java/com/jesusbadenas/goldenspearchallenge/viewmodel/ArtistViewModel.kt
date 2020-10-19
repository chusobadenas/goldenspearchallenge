package com.jesusbadenas.goldenspearchallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.jesusbadenas.goldenspearchallenge.data.model.Artist
import com.jesusbadenas.goldenspearchallenge.domain.repositories.SearchRepository
import com.jesusbadenas.goldenspearchallenge.util.toUIError

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

    fun handleState(loadState: CombinedLoadStates, itemCount: Int) {
        // Show progress bar
        val isLoading =
            loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading
        emptyTextVisible.value = !isLoading && itemCount == 0
        loadingVisible.value = isLoading
        // Show error
        when {
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            else -> null
        }?.let { errorState ->
            uiError.value = errorState.error.toUIError()
        }
    }
}
