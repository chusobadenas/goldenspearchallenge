package com.jesusbadenas.goldenspearchallenge.viewmodel

import androidx.lifecycle.viewModelScope
import com.jesusbadenas.goldenspearchallenge.common.LiveEvent
import com.jesusbadenas.goldenspearchallenge.domain.repositories.AuthRepository

class SplashViewModel(private val authRepository: AuthRepository) : BaseViewModel() {

    val navigateAction = LiveEvent<Void>()

    fun refreshToken() {
        viewModelScope.safeLaunch {
            authRepository.updateAccessToken()
            navigateAction.call()
        }
    }
}
