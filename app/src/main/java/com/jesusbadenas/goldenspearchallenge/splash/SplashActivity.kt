package com.jesusbadenas.goldenspearchallenge.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jesusbadenas.goldenspearchallenge.R
import com.jesusbadenas.goldenspearchallenge.navigation.Navigator
import com.jesusbadenas.goldenspearchallenge.viewmodel.SplashViewModel
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {

    private val navigator: Navigator by inject()
    private val viewModel: SplashViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        subscribe()
    }

    override fun onStart() {
        super.onStart()
        refreshToken()
    }

    private fun subscribe() {
        viewModel.navigateAction.observe(this) {
            navigateToArtist()
        }
    }

    private fun refreshToken() {
        viewModel.refreshToken()
    }

    private fun navigateToArtist() {
        navigator.navigateToArtistActivity(this)
        finish()
    }
}
