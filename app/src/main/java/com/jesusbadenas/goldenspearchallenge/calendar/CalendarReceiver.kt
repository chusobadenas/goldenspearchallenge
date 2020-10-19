package com.jesusbadenas.goldenspearchallenge.calendar

import android.accounts.AccountManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.provider.CalendarContract
import com.jesusbadenas.goldenspearchallenge.model.CalendarEvent
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.Date
import java.util.GregorianCalendar

class CalendarReceiver : BroadcastReceiver(), KoinComponent {

    private val calendarEventsManager: CalendarEventsManager by inject()

    private val calendarProjection: Array<String> = arrayOf(
        CalendarContract.Calendars._ID,                     // 0
        CalendarContract.Calendars.ACCOUNT_NAME,            // 1
        CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,   // 2
        CalendarContract.Calendars.OWNER_ACCOUNT            // 3
    )

    private val eventsProjection: Array<String> = arrayOf(
        CalendarContract.Events._ID,                        // 0
        CalendarContract.Events.CALENDAR_ID,                // 1
        CalendarContract.Events.TITLE,                      // 2
        CalendarContract.Events.DESCRIPTION,                // 3
        CalendarContract.Events.DTSTART                     // 4
    )

    override fun onReceive(context: Context?, intent: Intent?) {
        // Get primary email
        val email = getUserEmail(context)
        // Get calendar id
        val calendarId = getCalendarId(context, email)
        // Get today events
        val todayEvents = getTodayEvents(context, calendarId)
        calendarEventsManager.scheduleEvents(todayEvents)
    }

    private fun getUserEmail(context: Context?): String? =
        AccountManager.get(context).getAccountsByType(GOOGLE_ACCOUNT_TYPE).let { accounts ->
            if (accounts.isNotEmpty()) accounts[0].name else null
        }

    private fun getCalendarId(context: Context?, userEmail: String?): Long? {
        var calendarId: Long? = null
        val uri = CalendarContract.Calendars.CONTENT_URI
        val selection = "((${CalendarContract.Calendars.ACCOUNT_NAME} = ?) AND (" +
                "${CalendarContract.Calendars.ACCOUNT_TYPE} = ?) AND (" +
                "${CalendarContract.Calendars.OWNER_ACCOUNT} = ?))"
        val selectionArgs = arrayOf(userEmail, GOOGLE_ACCOUNT_TYPE, userEmail)

        val cursor: Cursor? =
            context?.contentResolver?.query(uri, calendarProjection, selection, selectionArgs, null)
        if (cursor != null && cursor.moveToNext()) {
            calendarId = cursor.getLong(PROJECTION_ID_INDEX)
            cursor.close()
        }

        return calendarId
    }

    private fun getTodayEvents(context: Context?, calendarId: Long?): List<CalendarEvent> {
        val events = mutableListOf<CalendarEvent>()
        val uri = CalendarContract.Events.CONTENT_URI
        val selection = "((${CalendarContract.Events.CALENDAR_ID} = ?) AND (" +
                "${CalendarContract.Events.TITLE} = ?) AND (" +
                "${CalendarContract.Events.DTSTART} >= ?) AND (" +
                "${CalendarContract.Events.DTEND} <= ?))"
        val now = Date().time
        val tomorrow = GregorianCalendar().apply { add(GregorianCalendar.DATE, 1) }.timeInMillis
        val selectionArgs = arrayOf(
            calendarId.toString(),
            GOLDENTIFY_EVENT_TITLE,
            now.toString(),
            tomorrow.toString()
        )

        val cursor: Cursor? =
            context?.contentResolver?.query(uri, eventsProjection, selection, selectionArgs, null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val id = cursor.getLong(PROJECTION_ID_INDEX)
                val description = cursor.getString(PROJECTION_DESCRIPTION_INDEX)
                val start = cursor.getLong(PROJECTION_DATE_START_INDEX)
                events.add(CalendarEvent(id, description, start))
            }
            cursor.close()
        }

        return events
    }

    companion object {
        private const val GOLDENTIFY_EVENT_TITLE = "GOLDENTIFY"
        private const val GOOGLE_ACCOUNT_TYPE = "com.google"
        private const val PROJECTION_ID_INDEX: Int = 0
        private const val PROJECTION_DESCRIPTION_INDEX: Int = 3
        private const val PROJECTION_DATE_START_INDEX: Int = 4
    }
}
