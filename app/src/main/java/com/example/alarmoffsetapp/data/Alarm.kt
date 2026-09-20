package com.example.alarmoffsetapp.data

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class Alarm(
    val id: Int,
    val name: String,
    val baseTime: LocalTime,
    val isRepeating: Boolean,
    val scheduledDate: LocalDate?,
    val daysOfWeek: Set<DayOfWeek>?,
    val isVibrating: Boolean,
    val isActive: Boolean,
    val canDismissOffsets: Boolean,
    val alarmOffsets: List<AlarmOffset>,
) {
    fun getNextBaseAlarm(): LocalDateTime {
        val now = LocalDateTime.now()

        if (!isRepeating) { // if alarm is not repeating and has a next date
            if (scheduledDate == null) {
                throw Exception("Alarm is not repeating and has no next date") // TODO: update with better exception
            }

            val baseDateTime = LocalDateTime.of(scheduledDate, baseTime)

            // if base time after current time, return base time
            if (baseDateTime.isAfter(now)) return baseDateTime

            return LocalDateTime.of(LocalDateTime.now().toLocalDate().plusDays(1), baseTime) // else, return alarm time tomorrow

        } else { // alarm is repeating

            if (daysOfWeek?.isEmpty() == true) {
                throw Exception("Alarm is repeating but has no repeating days") // TODO: update with better exception
            }

            val nextDateTime = now.with(baseTime)

            // if alarm on current day is active and time of next alarm is in the future, return time
            if (daysOfWeek?.contains(nextDateTime.dayOfWeek) == true && nextDateTime.isAfter(now)) {
                return nextDateTime
            }

            // return base time on next repeating day
            while (true) {
                nextDateTime.plusDays(1)
                if (daysOfWeek?.contains(nextDateTime.dayOfWeek) == true ) return nextDateTime // if day of week is not in the list of active days, continue
            }
        }
    }
    fun getNextAlarm(): LocalDateTime {

        val now = LocalDateTime.now()

        if (!isRepeating) { // if alarm is not repeating and has a next date
            if (scheduledDate == null) {
                throw Exception("Alarm is not repeating and has no next date") // TODO: update with better exception
            }

            val baseDateTime = LocalDateTime.of(scheduledDate, baseTime)

            // if base time after current time, return base time
            if (baseDateTime.isAfter(now)) return baseDateTime

            // check through all active offsets and  return the offset time if it is are after current time. relies on sorted list
            for (alarmOffset in alarmOffsets) {
                val offsetDateTime = baseDateTime.plus(alarmOffset.offset)
                if (offsetDateTime.isAfter(now) && alarmOffset.isActive)
                    return offsetDateTime
            }

            return LocalDateTime.of(LocalDateTime.now().toLocalDate().plusDays(1), baseTime) // else, return alarm time tomorrow

        } else { // alarm is repeating

            if (daysOfWeek?.isEmpty() == true) {
                throw Exception("Alarm is repeating but has no repeating days") // TODO: update with better exception
            }

            val nextDateTime = now.with(baseTime)

            // if alarm on current day is active, return time
            if (daysOfWeek?.contains(nextDateTime.dayOfWeek) == true ) {
                if (nextDateTime.isAfter(now)) return nextDateTime // if time of next alarm is in the future

                // if time of the smallest alarm offset is in the future
                for (alarmOffset in alarmOffsets) {
                    val offsetDateTime = nextDateTime.plus(alarmOffset.offset)
                    if (offsetDateTime.isAfter(now) && alarmOffset.isActive)
                        return offsetDateTime
                }
            }

            // return base time on next repeating day
            while (true) {
                nextDateTime.plusDays(1)
                if (daysOfWeek?.contains(nextDateTime.dayOfWeek) == true ) return nextDateTime // if day of week is not in the list of active days, continue
            }
        }
    }
}


