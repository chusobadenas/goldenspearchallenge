package com.jesusbadenas.goldenspearchallenge.artist

import android.app.SearchManager
import android.content.Intent
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
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        var query: String? = null
        if (Intent.ACTION_SEARCH == intent?.action) {
            query = intent.getStringExtra(SearchManager.QUERY)
        }
        startArtistFragment(query)
    }

    private fun startArtistFragment(query: String?) {
        navigator.navigateToArtistFragment(this, query)
    }
}
