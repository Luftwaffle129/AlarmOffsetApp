package com.example.alarmoffsetapp.data

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class AlarmTest {
    /**
     * Tests getNextBaseAlarm(). If alarm is not repeating and has a past scheduled date, but a future base time relative to the current time, set the next alarm for today
     */
    @Test
    fun alarm_getNextBaseAlarm_pastAlarmIsNextToday() {
        val currentDay = LocalDate.now()

        val alarm = Alarm(
            id = 0,
            name = "test",
            baseTime = LocalTime.now().plusMinutes(10),
            isRepeating = false,
            scheduledDate = currentDay.minusDays(1),
            daysOfWeek = setOf(),
            isVibrating = true,
            playsSound = true,
            alarmGroup = null,
            alarmOffsets = listOf(),
            isActive = false,
            canDismissOffsets = true
        )

        assertEquals(LocalDateTime.of(currentDay.plusDays(1), alarm.baseTime), alarm.getNextBaseAlarm())
    }
    /**
     * Tests getNextBaseAlarm(). If alarm is not repeating and has a past scheduled date and a past base time relative to the current time, set the next alarm for today
     */
    @Test
    fun alarm_getNextBaseAlarm_pastAlarmIsNextTomorrow() {
        val currentDay = LocalDate.now()
        val currentTime = LocalTime.now()

        val alarm = Alarm(
            id = 0,
            name = "test",
            baseTime = currentTime.minusMinutes(10),
            isRepeating = false,
            scheduledDate = currentDay.minusDays(1),
            daysOfWeek = setOf(),
            isVibrating = true,
            playsSound = true,
            alarmGroup = null,
            alarmOffsets = listOf(),
            isActive = false,
            canDismissOffsets = true
        )

        assertEquals(LocalDateTime.of(currentDay.plusDays(1), currentTime.minusMinutes(10)), alarm.getNextBaseAlarm())
    }
}