package com.jesusbadenas.goldenspearchallenge.util

import com.jesusbadenas.goldenspearchallenge.R
import org.junit.Assert
import org.junit.Test

class ExtensionsTest {

    @Test
    fun testThrowableToUIErrorSuccess() {
        val exception = Exception("This is an exception")

        val uiError = exception.toUIError()

        Assert.assertEquals(exception, uiError.throwable)
        Assert.assertEquals(R.string.generic_error_message, uiError.messageResId)
    }
}
