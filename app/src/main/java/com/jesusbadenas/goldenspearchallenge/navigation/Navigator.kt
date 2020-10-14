package com.jesusbadenas.goldenspearchallenge.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
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

    fun navigateToArtistFragment(activity: AppCompatActivity, query: String?) {
        activity.supportFragmentManager.beginTransaction().apply {
            val args = Bundle().apply {
                putString(QUERY_ARG_KEY, query)
            }
            replace(R.id.fragmentContainer, ArtistFragment::class.java, args)
            commit()
        }
    }

    companion object {
        const val QUERY_ARG_KEY = "query_arg"
    }
}
