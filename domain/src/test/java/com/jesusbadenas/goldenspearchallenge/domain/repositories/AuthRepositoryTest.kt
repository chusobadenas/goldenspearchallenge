package com.jesusbadenas.goldenspearchallenge.domain.repositories

import com.jesusbadenas.goldenspearchallenge.data.api.AuthorizationService
import com.jesusbadenas.goldenspearchallenge.data.api.response.ApiTokenResponse
import com.jesusbadenas.goldenspearchallenge.test.CoroutinesTestRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AuthRepositoryTest {

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    @MockK
    private lateinit var authService: AuthorizationService

    private lateinit var authRepository: AuthRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        authRepository = AuthRepository(authService)
    }

    @Test
    fun testGetAccessTokenSuccess() {
        val apiTokenResponse =
            ApiTokenResponse(accessToken = "NgCXRKc", tokenType = "bearer", expiresIn = 3600)
        coEvery { authService.getApiToken() } returns apiTokenResponse

        val token = runBlocking { authRepository.getAccessToken() }

        Assert.assertEquals("NgCXRKc", token)
    }
}
