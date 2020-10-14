package com.jesusbadenas.goldenspearchallenge.domain.repositories

import com.jesusbadenas.goldenspearchallenge.data.api.AuthorizationService
import com.jesusbadenas.goldenspearchallenge.data.preferences.SharedPreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date

class AuthRepository(
    private val authService: AuthorizationService,
    private val sharedPrefs: SharedPreferencesManager
) {

    suspend fun updateAccessToken() =
        withContext(Dispatchers.IO) {
            val now = Date().time
            val storedToken: String? = sharedPrefs.getAccessToken()
            val storedExpirationTime: Long = sharedPrefs.getTokenExpirationTime()

            // No token stored or expiration time gone
            if (storedToken == null || now >= storedExpirationTime) {
                val newToken = authService.getApiToken().accessToken
                val newExpirationTime = now + authService.getApiToken().expiresIn * SEC_TO_MS
                sharedPrefs.saveAccessToken(newToken)
                sharedPrefs.saveTokenExpirationTime(newExpirationTime)
            }
        }

    companion object {
        private const val SEC_TO_MS: Long = 1000
    }
}
