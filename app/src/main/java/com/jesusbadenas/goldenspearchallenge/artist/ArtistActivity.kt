package com.jesusbadenas.goldenspearchallenge.artist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jesusbadenas.goldenspearchallenge.R
import com.jesusbadenas.goldenspearchallenge.navigation.Navigator
import org.koin.android.ext.android.inject

class ArtistActivity : AppCompatActivity() {

    private val navigator: Navigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.default_activity)
        startArtistFragment()
    }

    private fun startArtistFragment() {
        navigator.navigateToArtistFragment(this)
    }
}
