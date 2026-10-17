package com.example.alarmoffsetapp.fake

import com.example.alarmoffsetapp.data.Alarm
import com.example.alarmoffsetapp.data.AlarmGroup
import com.example.alarmoffsetapp.data.AlarmOffset
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime

class FakeDataSource {
    private val alarmOffset = AlarmOffset(
        id = 0,
        offset = Duration.ofMinutes(10),
        isActive = true
    )

    val alarmOffsetList = mutableListOf(alarmOffset, alarmOffset.copy(offset = Duration.ofMinutes(61), isActive = false), alarmOffset)

    private val alarm = Alarm(
        id = 0,
        name = "test",
        baseTime = LocalTime.now().plusMinutes(10),
        isRepeating = false,
        scheduledDate = LocalDate.now(),
        daysOfWeek = setOf(),
        isVibrating = true,
        playsSound = true,
        alarmGroup = null,
        alarmOffsets = listOf(),
        isActive = false,
        canDismissOffsets = true
    )
    val alarmList = mutableListOf(alarm.copy(alarmOffsets = alarmOffsetList), alarm.copy(id=1,isActive = true), alarm.copy(id=2, scheduledDate = null, isRepeating = true, daysOfWeek = setOf(
        DayOfWeek.TUESDAY)))
    private val alarmGroup = AlarmGroup(
        id = 0,
        name = "test",
        alarms = alarmList,
        isActive = false,
    )
    val alarmGroupList = mutableListOf(alarmGroup, alarmGroup.copy(isActive = true), alarmGroup)
}