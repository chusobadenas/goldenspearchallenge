package com.jesusbadenas.goldenspearchallenge.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.jesusbadenas.goldenspearchallenge.data.model.Artist
import com.jesusbadenas.goldenspearchallenge.domain.repositories.SearchRepository
import com.jesusbadenas.goldenspearchallenge.test.CoroutinesTestRule
import com.jesusbadenas.goldenspearchallenge.test.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtistViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    @MockK
    private lateinit var searchRepository: SearchRepository

    private lateinit var viewModel: ArtistViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = ArtistViewModel(searchRepository)
    }

    @Test
    fun testSearchArtistsSuccess() = coroutineRule.runBlockingTest {
        val artist = Artist(id = "1", name = "John Doe", imageUrl = "https://i.scdn.co/image/1")
        val pagingData = PagingData.from(listOf(artist))
        val liveData = MutableLiveData(pagingData)
        every { searchRepository.searchArtists("john", viewModel.viewModelScope) } returns liveData

        viewModel.searchArtists("john")

        val result = viewModel.artists.getOrAwaitValue()

        Assert.assertNotNull(result)
    }

    @Test
    fun testHandleStateIsLoading() = coroutineRule.runBlockingTest {
        val loadState = CombinedLoadStates(
            LoadStates(
                refresh = LoadState.Loading,
                prepend = LoadState.NotLoading(false),
                append = LoadState.NotLoading(false)
            ), null
        )

        viewModel.handleState(loadState, 10)

        val emptyTextVisible = viewModel.emptyTextVisible.getOrAwaitValue()
        val loadingVisible = viewModel.loadingVisible.getOrAwaitValue()

        Assert.assertFalse(emptyTextVisible)
        Assert.assertTrue(loadingVisible)
    }

    @Test
    fun testHandleStateEmptyState() = coroutineRule.runBlockingTest {
        val loadState = CombinedLoadStates(
            LoadStates(
                refresh = LoadState.NotLoading(false),
                prepend = LoadState.NotLoading(true),
                append = LoadState.NotLoading(true)
            ), null
        )

        viewModel.handleState(loadState, 0)

        val emptyTextVisible = viewModel.emptyTextVisible.getOrAwaitValue()
        val loadingVisible = viewModel.loadingVisible.getOrAwaitValue()

        Assert.assertTrue(emptyTextVisible)
        Assert.assertFalse(loadingVisible)
    }

    @Test
    fun testHandleStateIsError() = coroutineRule.runBlockingTest {
        val throwable = Exception()
        val loadState = CombinedLoadStates(
            LoadStates(
                refresh = LoadState.Error(throwable),
                prepend = LoadState.NotLoading(true),
                append = LoadState.NotLoading(true)
            ), null
        )

        viewModel.handleState(loadState, 10)

        val emptyTextVisible = viewModel.emptyTextVisible.getOrAwaitValue()
        val loadingVisible = viewModel.loadingVisible.getOrAwaitValue()
        val uiError = viewModel.uiError.getOrAwaitValue()

        Assert.assertFalse(emptyTextVisible)
        Assert.assertFalse(loadingVisible)
        Assert.assertEquals(throwable, uiError.throwable)
    }
}
