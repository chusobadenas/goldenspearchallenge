package com.jesusbadenas.goldenspearchallenge.domain.datasource

import androidx.paging.PagingSource
import com.jesusbadenas.goldenspearchallenge.data.api.APIService
import com.jesusbadenas.goldenspearchallenge.data.model.Artist
import com.jesusbadenas.goldenspearchallenge.data.util.toArtist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ArtistsDataSource(
    private val apiService: APIService,
    private val query: String
) : PagingSource<Int, Artist>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Artist> {
        // Pagination
        val page: Int = params.key ?: 0
        val limit: Int = params.loadSize
        val offset: Int = page * params.loadSize

        // API call
        return try {
            val artists = withContext(Dispatchers.IO) {
                apiService.searchArtists(
                    query = query,
                    limit = limit,
                    offset = offset
                ).artists.items.map { it.toArtist() }
            }

            // Next page
            val nextKey = if (artists.isNotEmpty()) page + 1 else null

            // Result OK
            LoadResult.Page(data = artists, prevKey = null, nextKey = nextKey)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}
