package com.jesusbadenas.goldenspearchallenge.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jesusbadenas.goldenspearchallenge.R
import com.jesusbadenas.goldenspearchallenge.domain.repositories.AuthRepository
import com.jesusbadenas.goldenspearchallenge.test.CoroutinesTestRule
import com.jesusbadenas.goldenspearchallenge.test.getOrAwaitValue
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SplashViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    @MockK
    private lateinit var authRepository: AuthRepository

    @MockK
    private lateinit var actionObserver: Observer<Void>

    private lateinit var viewModel: SplashViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = SplashViewModel(authRepository)
    }

    @Test
    fun testRefreshTokenSuccess() = coroutineRule.runBlockingTest {
        coEvery { authRepository.updateAccessToken() } just Runs
        every { actionObserver.onChanged(null) } just Runs

        viewModel.navigateAction.observeForever(actionObserver)
        viewModel.refreshToken()

        coVerify { authRepository.updateAccessToken() }
        verify { actionObserver.onChanged(null) }
    }

    @Test
    fun testRefreshTokenThrowsException() = coroutineRule.runBlockingTest {
        val exception = Exception()
        coEvery { authRepository.updateAccessToken() } throws exception

        viewModel.refreshToken()
        val uiError = viewModel.uiError.getOrAwaitValue()

        Assert.assertEquals(exception, uiError.throwable)
        Assert.assertEquals(R.string.generic_error_message, uiError.messageResId)
    }
}
