package com.jesusbadenas.goldenspearchallenge.calendar

import android.app.AlarmManager
import android.app.AlarmManager.RTC_WAKEUP
import android.content.Context
import android.content.Intent
import com.jesusbadenas.goldenspearchallenge.model.CalendarEvent
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test
import java.util.Date

class CalendarEventsManagerTest {

    @RelaxedMockK
    private lateinit var context: Context

    @RelaxedMockK
    private lateinit var alarmManager: AlarmManager

    private lateinit var calendarEventsManager: CalendarEventsManager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        every { context.getSystemService(Context.ALARM_SERVICE) } returns alarmManager

        calendarEventsManager = CalendarEventsManager(context)
    }

    @Test
    fun testScheduleEventsSuccess() {
        val intent1 = mockk<Intent>(relaxed = true)
        val intent2 = mockk<Intent>(relaxed = true)

        mockkConstructor(Intent::class)
        every { anyConstructed<Intent>().putExtra("event_description", "Nirvana") } returns intent1
        every { anyConstructed<Intent>().putExtra("event_description", "Suu") } returns intent2

        val time1 = Date().time
        val time2 = time1 * 2
        every {
            alarmManager.setExactAndAllowWhileIdle(RTC_WAKEUP, time1, any())
        } just Runs
        every {
            alarmManager.setExactAndAllowWhileIdle(RTC_WAKEUP, time2, any())
        } just Runs

        val event1 = CalendarEvent(id = 1, description = "Nirvana", startDate = time1)
        val event2 = CalendarEvent(id = 2, description = "Suu", startDate = time2)
        val events = listOf(event1, event1, event2)

        calendarEventsManager.scheduleEvents(events)

        verify { alarmManager.setExactAndAllowWhileIdle(RTC_WAKEUP, time1, any()) }
        verify { alarmManager.setExactAndAllowWhileIdle(RTC_WAKEUP, time2, any()) }
    }
}
