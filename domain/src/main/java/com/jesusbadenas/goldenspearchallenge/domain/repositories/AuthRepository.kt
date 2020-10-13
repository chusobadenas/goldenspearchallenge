package com.jesusbadenas.goldenspearchallenge.domain.repositories

import com.jesusbadenas.goldenspearchallenge.data.api.AuthorizationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(private val authService: AuthorizationService) {

    suspend fun getAccessToken(): String =
        withContext(Dispatchers.IO) {
            authService.getApiToken().accessToken
        }
}
