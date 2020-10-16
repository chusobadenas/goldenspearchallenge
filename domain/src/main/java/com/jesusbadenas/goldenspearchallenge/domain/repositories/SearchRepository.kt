package com.jesusbadenas.goldenspearchallenge.domain.repositories

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.jesusbadenas.goldenspearchallenge.data.api.APIService
import com.jesusbadenas.goldenspearchallenge.data.model.Artist
import com.jesusbadenas.goldenspearchallenge.domain.datasource.ArtistsDataSource
import kotlinx.coroutines.CoroutineScope

class SearchRepository(private val apiService: APIService) {

    private val pagingConfig = PagingConfig(
        enablePlaceholders = false,
        initialLoadSize = PAGE_SIZE,
        pageSize = PAGE_SIZE
    )

    fun searchArtists(query: String, scope: CoroutineScope): LiveData<PagingData<Artist>> =
        Pager(config = pagingConfig) {
            ArtistsDataSource(apiService, query)
        }.liveData.cachedIn(scope)

    companion object {
        private const val PAGE_SIZE = 6
    }
}
