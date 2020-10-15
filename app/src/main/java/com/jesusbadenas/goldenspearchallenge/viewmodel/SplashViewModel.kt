package com.jesusbadenas.goldenspearchallenge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesusbadenas.goldenspearchallenge.common.LiveEvent
import com.jesusbadenas.goldenspearchallenge.domain.repositories.AuthRepository
import com.jesusbadenas.goldenspearchallenge.model.UIError
import com.jesusbadenas.goldenspearchallenge.util.toUIError
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class SplashViewModel(private val authRepository: AuthRepository) : ViewModel() {

    val navigateAction = LiveEvent<Void>()
    val uiError = MutableLiveData<UIError>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        uiError.value = throwable.toUIError()
    }

    fun refreshToken() {
        viewModelScope.launch(exceptionHandler) {
            authRepository.updateAccessToken()
            navigateAction.call()
        }
    }
}
