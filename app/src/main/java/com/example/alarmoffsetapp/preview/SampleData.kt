package com.example.alarmoffsetapp.preview

import com.example.alarmoffsetapp.data.Alarm
import com.example.alarmoffsetapp.data.AlarmGroup
import com.example.alarmoffsetapp.data.AlarmOffset
import com.example.alarmoffsetapp.ui.home.HomeUiState
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

object SampleData {
    val alarmOffset = AlarmOffset(
        offset = Duration.ofMinutes(10),
        isActive = true
    )

    val alarmOffsets = listOf(alarmOffset, alarmOffset.copy(offset = Duration.ofMinutes(61), isActive = false), alarmOffset)

    val alarm = Alarm(
        id = 0,
        name = "test",
        baseTime = LocalTime.now().plusMinutes(0),
        isRepeating = false,
        scheduledDate = LocalDate.now(),
        daysOfWeek = setOf(),
        isVibrating = true,
        playsSound = true,
        alarmOffsets = alarmOffsets,
        isActive = false,
        canDismissOffsets = true
    )
    val alarms = listOf(alarm, alarm.copy(isActive = true), alarm)
    val alarmGroup = AlarmGroup(
        id = 0,
        name = "test",
        alarms = alarms,
        isActive = false,
    )
    val alarmGroups = listOf(alarmGroup, alarmGroup.copy(isActive = true), alarmGroup)

    val homeScreenUiState = HomeUiState(
        nextAlarm = LocalDateTime.now(),
        timeUntilNextAlarm = Duration.ofDays(1),
        alarmGroups = alarmGroups,
        individualAlarms = alarms
    )
}