package com.jesusbadenas.goldenspearchallenge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jesusbadenas.goldenspearchallenge.R
import com.jesusbadenas.goldenspearchallenge.model.UIError
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    val uiError = MutableLiveData<UIError>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        uiError.value = UIError(throwable, R.string.generic_error_message)
    }

    fun CoroutineScope.safeLaunch(body: suspend () -> Unit): Job =
        launch(exceptionHandler) {
            body.invoke()
        }
}
