package com.jesusbadenas.goldenspearchallenge.util

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import androidx.fragment.app.Fragment
import com.jesusbadenas.goldenspearchallenge.R
import com.jesusbadenas.goldenspearchallenge.model.UIError
import timber.log.Timber

private fun showError(context: Context, uiError: UIError) {
    // Log error
    Timber.e(uiError.throwable)
    // Show dialog
    AlertDialog.Builder(context).apply {
        setCancelable(false)
        setTitle(R.string.generic_error_title)
        setMessage(uiError.messageResId)
        setNeutralButton(android.R.string.ok, null)
        create()
    }.show()
}

fun Activity.showError(uiError: UIError) {
    showError(this, uiError)
}

fun Fragment.showError(uiError: UIError) {
    showError(requireActivity(), uiError)
}

fun Throwable.toUIError(): UIError = UIError(this, R.string.generic_error_message)
