package com.jesusbadenas.goldenspearchallenge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jesusbadenas.goldenspearchallenge.data.api.APIService
import com.jesusbadenas.goldenspearchallenge.data.entities.Artist
import com.jesusbadenas.goldenspearchallenge.domain.datasource.ArtistsDataSource
import kotlinx.coroutines.flow.Flow

class ArtistViewModel(private val apiService: APIService) : ViewModel() {

    private val pagingConfig = PagingConfig(
        enablePlaceholders = false,
        initialLoadSize = PAGE_SIZE,
        pageSize = PAGE_SIZE
    )

    // TODO
    val emptyTextVisible = MutableLiveData(false)

    fun searchArtists(query: String): Flow<PagingData<Artist>> =
        Pager(config = pagingConfig) { ArtistsDataSource(apiService, query) }
            .flow
            .cachedIn(viewModelScope)

    companion object {
        private const val PAGE_SIZE = 20
    }
}
