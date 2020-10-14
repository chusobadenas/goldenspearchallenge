package com.jesusbadenas.goldenspearchallenge.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jesusbadenas.goldenspearchallenge.domain.repositories.AuthRepository
import com.jesusbadenas.goldenspearchallenge.test.CoroutinesTestRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
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
}
