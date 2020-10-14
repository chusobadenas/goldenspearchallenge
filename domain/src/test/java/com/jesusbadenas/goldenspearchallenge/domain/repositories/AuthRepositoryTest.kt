package com.jesusbadenas.goldenspearchallenge.domain.repositories

import com.jesusbadenas.goldenspearchallenge.data.api.AuthorizationService
import com.jesusbadenas.goldenspearchallenge.data.api.response.ApiTokenResponse
import com.jesusbadenas.goldenspearchallenge.data.preferences.SharedPreferencesManager
import com.jesusbadenas.goldenspearchallenge.test.CoroutinesTestRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date

@ExperimentalCoroutinesApi
class AuthRepositoryTest {

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    @MockK
    private lateinit var authService: AuthorizationService

    @MockK
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    private lateinit var authRepository: AuthRepository

    private val apiTokenResponse =
        ApiTokenResponse(accessToken = "t0k3n", tokenType = "bearer", expiresIn = 3600)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        authRepository = AuthRepository(authService, sharedPreferencesManager)
    }

    @Test
    fun testUpdateAccessTokenFirstTime() {
        every { sharedPreferencesManager.getAccessToken() } returns null
        every { sharedPreferencesManager.getTokenExpirationTime() } returns 0L
        coEvery { authService.getApiToken() } returns apiTokenResponse
        every { sharedPreferencesManager.saveAccessToken("t0k3n") } just Runs
        every { sharedPreferencesManager.saveTokenExpirationTime(any()) } just Runs

        runBlocking { authRepository.updateAccessToken() }

        verify { sharedPreferencesManager.saveAccessToken("t0k3n") }
        verify { sharedPreferencesManager.saveTokenExpirationTime(any()) }
    }

    @Test
    fun testUpdateAccessTokenTimeExpired() {
        every { sharedPreferencesManager.getAccessToken() } returns "t0k3n"
        every { sharedPreferencesManager.getTokenExpirationTime() } returns 0L
        coEvery { authService.getApiToken() } returns apiTokenResponse
        every { sharedPreferencesManager.saveAccessToken("t0k3n") } just Runs
        every { sharedPreferencesManager.saveTokenExpirationTime(any()) } just Runs

        runBlocking { authRepository.updateAccessToken() }

        verify { sharedPreferencesManager.saveAccessToken("t0k3n") }
        verify { sharedPreferencesManager.saveTokenExpirationTime(any()) }
    }

    @Test
    fun testUpdateAccessTokenNotNeeded() {
        every { sharedPreferencesManager.getAccessToken() } returns "t0k3n"
        every { sharedPreferencesManager.getTokenExpirationTime() } returns Date().time + 3600L

        runBlocking { authRepository.updateAccessToken() }

        verify(exactly = 0) { sharedPreferencesManager.saveAccessToken("t0k3n") }
        verify(exactly = 0) { sharedPreferencesManager.saveTokenExpirationTime(any()) }
    }
}
