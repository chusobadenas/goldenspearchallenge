package com.jesusbadenas.goldenspearchallenge.navigation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.jesusbadenas.goldenspearchallenge.R
import com.jesusbadenas.goldenspearchallenge.artist.ArtistActivity
import com.jesusbadenas.goldenspearchallenge.artist.ArtistFragment

class Navigator {

    fun navigateToArtistActivity(context: Context) {
        Intent(context, ArtistActivity::class.java).let { intent ->
            context.startActivity(intent)
        }
    }

    fun navigateToArtistFragment(activity: AppCompatActivity) {
        activity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, ArtistFragment::class.java, null)
            .commit()
    }
}
