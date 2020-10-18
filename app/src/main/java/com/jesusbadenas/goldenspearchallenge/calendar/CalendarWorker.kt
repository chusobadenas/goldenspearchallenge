package com.jesusbadenas.goldenspearchallenge.calendar

import android.accounts.AccountManager
import android.content.Context
import android.database.Cursor
import android.provider.CalendarContract
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.jesusbadenas.goldenspearchallenge.model.CalendarEvent

class CalendarWorker(val context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        // Get primary email
        val email = getUserEmail()
        // Get calendar id
        val calendarId = getCalendarId(email)
        // Get events
        val events = getEvents(calendarId, dateStart = 0, dateEnd = 0)
        // Schedule search
        scheduleSearch(events)
        // Success
        return Result.success()
    }

    private val calendarProjection: Array<String> = arrayOf(
        CalendarContract.Calendars._ID,                     // 0
        CalendarContract.Calendars.ACCOUNT_NAME,            // 1
        CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,   // 2
        CalendarContract.Calendars.OWNER_ACCOUNT            // 3
    )

    private val eventsProjection: Array<String> = arrayOf(
        CalendarContract.Events.CALENDAR_ID,                // 0
        CalendarContract.Events.TITLE,                      // 1
        CalendarContract.Events.DESCRIPTION,                // 2
        CalendarContract.Events.DTSTART                     // 3
    )

    private fun getUserEmail(): String? =
        AccountManager.get(context).getAccountsByType(GOOGLE_ACCOUNT_TYPE).let { accounts ->
            if (accounts.isNotEmpty()) accounts[0].name else null
        }

    private fun getCalendarId(userEmail: String?): Long? {
        var calendarId: Long? = null
        val uri = CalendarContract.Calendars.CONTENT_URI
        val selection = "((${CalendarContract.Calendars.ACCOUNT_NAME} = ?) AND (" +
                "${CalendarContract.Calendars.ACCOUNT_TYPE} = ?) AND (" +
                "${CalendarContract.Calendars.OWNER_ACCOUNT} = ?))"
        val selectionArgs = arrayOf(userEmail, GOOGLE_ACCOUNT_TYPE, userEmail)

        val cursor: Cursor? =
            context.contentResolver.query(uri, calendarProjection, selection, selectionArgs, null)
        if (cursor != null && cursor.moveToNext()) {
            calendarId = cursor.getLong(PROJECTION_ID_INDEX)
            cursor.close()
        }

        return calendarId
    }

    private fun getEvents(calendarId: Long?, dateStart: Long, dateEnd: Long): List<CalendarEvent> {
        val events = mutableListOf<CalendarEvent>()
        val uri = CalendarContract.Events.CONTENT_URI
        val selection = "((${CalendarContract.Events.CALENDAR_ID} = ?) AND (" +
                "${CalendarContract.Events.DTSTART} >= ?) AND (" +
                "${CalendarContract.Events.DTEND} <= ?))"
        val selectionArgs = arrayOf(calendarId.toString(), dateStart.toString(), dateEnd.toString())

        val cursor: Cursor? =
            context.contentResolver.query(uri, eventsProjection, selection, selectionArgs, null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val title = cursor.getString(PROJECTION_TITLE_INDEX)
                val description = cursor.getString(PROJECTION_DESCRIPTION_INDEX)
                val start = cursor.getLong(PROJECTION_DATE_START_INDEX)
                events.add(CalendarEvent(title, description, start))
            }
            cursor.close()
        }

        return events
    }

    private fun scheduleSearch(events: List<CalendarEvent>) {
        // TODO
    }

    companion object {
        private const val GOOGLE_ACCOUNT_TYPE = "com.google"
        private const val PROJECTION_ID_INDEX: Int = 0
        private const val PROJECTION_TITLE_INDEX: Int = 1
        private const val PROJECTION_DESCRIPTION_INDEX: Int = 2
        private const val PROJECTION_DATE_START_INDEX: Int = 3
    }
}
