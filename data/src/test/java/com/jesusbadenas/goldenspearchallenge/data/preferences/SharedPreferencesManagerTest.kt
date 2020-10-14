package com.jesusbadenas.goldenspearchallenge.data.preferences

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import com.jesusbadenas.goldenspearchallenge.data.R
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SharedPreferencesManagerTest {

    private lateinit var sharedPrefsManager: SharedPreferencesManager

    @MockK(relaxed = true)
    private lateinit var context: Context

    @MockK
    private lateinit var editor: SharedPreferences.Editor

    @MockK
    private lateinit var resources: Resources

    @MockK
    private lateinit var sharedPrefs: SharedPreferences

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        every { context.resources } returns resources
        every { resources.getString(R.string.shared_prefs_file) } returns "shared_prefs_test"
        every {
            context.getSharedPreferences(
                "shared_prefs_test",
                Context.MODE_PRIVATE
            )
        } returns sharedPrefs
        every { sharedPrefs.edit() } returns editor

        sharedPrefsManager = SharedPreferencesManager(context)
    }

    @Test
    fun testGetAccessTokenSuccess() {
        every { sharedPrefs.getString("access_token", null) } returns "t0k3n"

        val result = sharedPrefsManager.getAccessToken()

        Assert.assertEquals("t0k3n", result)
    }

    @Test
    fun testSaveAccessTokenSuccess() {
        every { editor.putString("access_token", "t0k3n") } returns editor
        every { editor.apply() } just Runs

        sharedPrefsManager.saveAccessToken("t0k3n")

        verify { editor.putString("access_token", "t0k3n") }
        verify { editor.apply() }
    }

    @Test
    fun testGetExpirationTimeSuccess() {
        every { sharedPrefs.getLong("token_creation_time", 0L) } returns 3600

        val result = sharedPrefsManager.getTokenExpirationTime()

        Assert.assertEquals(3600, result)
    }

    @Test
    fun testSaveExpirationTimeSuccess() {
        every { editor.putLong("token_creation_time", 3600) } returns editor
        every { editor.apply() } just Runs

        sharedPrefsManager.saveTokenExpirationTime(3600)

        verify { editor.putLong("token_creation_time", 3600) }
        verify { editor.apply() }
    }
}
