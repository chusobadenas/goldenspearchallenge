package com.jesusbadenas.goldenspearchallenge.calendar

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.jesusbadenas.goldenspearchallenge.navigation.Navigator
import org.koin.core.KoinComponent
import org.koin.core.inject

class ScheduledEventReceiver : BroadcastReceiver(), KoinComponent {

    private val navigator: Navigator by inject()

    override fun onReceive(context: Context?, intent: Intent?) {
        val query = intent?.extras?.getString(EVENT_DESCRIPTION_EXTRA)
        navigator.navigateToArtistActivity(context!!, query)
    }

    companion object {
        const val EVENT_REQUEST_CODE = 1000
        const val EVENT_DESCRIPTION_EXTRA = "event_description"
    }
}
