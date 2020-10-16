package com.jesusbadenas.goldenspearchallenge.util

import android.app.AlertDialog
import android.content.Context
import androidx.fragment.app.Fragment
import com.jesusbadenas.goldenspearchallenge.R
import com.jesusbadenas.goldenspearchallenge.model.UIError
import timber.log.Timber

fun Context.showError(uiError: UIError) {
    // Log error
    Timber.e(uiError.throwable)
    // Show dialog
    AlertDialog.Builder(this).apply {
        setCancelable(false)
        setTitle(R.string.generic_error_title)
        setMessage(uiError.messageResId)
        setNeutralButton(android.R.string.ok, null)
        create()
    }.show()
}

fun Fragment.showError(uiError: UIError) {
    requireActivity().showError(uiError)
}

fun Throwable.toUIError(): UIError = UIError(this, R.string.generic_error_message)
