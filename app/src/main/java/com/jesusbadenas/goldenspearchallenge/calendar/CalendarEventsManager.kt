package com.jesusbadenas.goldenspearchallenge.calendar

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import com.jesusbadenas.goldenspearchallenge.calendar.ScheduledEventReceiver.Companion.EVENT_DESCRIPTION_EXTRA
import com.jesusbadenas.goldenspearchallenge.calendar.ScheduledEventReceiver.Companion.EVENT_REQUEST_CODE
import com.jesusbadenas.goldenspearchallenge.model.CalendarEvent

class CalendarEventsManager(private val context: Context) {

    private val events = mutableListOf<CalendarEvent>()

    private fun addEvent(event: CalendarEvent) {
        val eventAlreadyExists = events.map { it.id }.contains(event.id)
        if (!eventAlreadyExists) {
            events.add(event)
            scheduleEvent(event)
        }
    }

    fun addEvents(list: List<CalendarEvent>) {
        list.forEach { addEvent(it) }
    }

    private fun scheduleEvent(event: CalendarEvent) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val intent = Intent(context.applicationContext, ScheduledEventReceiver::class.java).apply {
            putExtra(EVENT_DESCRIPTION_EXTRA, event.description)
        }
        val pendingIntent =
            PendingIntent.getBroadcast(context, EVENT_REQUEST_CODE, intent, FLAG_UPDATE_CURRENT)
        alarmManager?.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            event.startDate,
            pendingIntent
        )
    }
}
