package com.jesusbadenas.goldenspearchallenge.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.jesusbadenas.goldenspearchallenge.data.R

class SharedPreferencesManager(context: Context) {

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val sharedPref: SharedPreferences = EncryptedSharedPreferences.create(
        context.resources.getString(R.string.shared_prefs_file),
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private fun <T> save(key: String, value: T?) {
        with(sharedPref.edit()) {
            when (value) {
                is Long -> putLong(key, value)
                is String -> putString(key, value)
            }
            apply()
        }
    }

    fun getAccessToken(): String? = sharedPref.getString(ACCESS_TOKEN_KEY, null)

    fun saveAccessToken(token: String?) = save(ACCESS_TOKEN_KEY, token)

    fun getTokenExpirationTime(): Long = sharedPref.getLong(TOKEN_CREATION_TIME_KEY, 0L)

    fun saveTokenExpirationTime(time: Long) = save(TOKEN_CREATION_TIME_KEY, time)

    companion object {
        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val TOKEN_CREATION_TIME_KEY = "token_creation_time"
    }
}
