package com.jesusbadenas.goldenspearchallenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesusbadenas.goldenspearchallenge.common.LiveEvent
import com.jesusbadenas.goldenspearchallenge.domain.repositories.AuthRepository
import kotlinx.coroutines.launch

class SplashViewModel(private val authRepository: AuthRepository) : ViewModel() {

    val navigateAction = LiveEvent<Void>()

    fun refreshToken() {
        viewModelScope.launch {
            authRepository.updateAccessToken()
            navigateAction.call()
        }
    }
}
