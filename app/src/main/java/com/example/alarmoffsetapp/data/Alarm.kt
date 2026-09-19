package com.example.alarmoffsetapp.data

import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime

data class Alarm(
    val id: Int,
    val name: String,
    val time: LocalTime,
    val isRepeating: Boolean,
    val daysOfWeek: Set<DayOfWeek>,
    val isVibrating: Boolean,
    val isActive: Boolean,
    val canSnoozeOffsets: Boolean,
    val alarmOffsets: List<AlarmOffset>,
)


