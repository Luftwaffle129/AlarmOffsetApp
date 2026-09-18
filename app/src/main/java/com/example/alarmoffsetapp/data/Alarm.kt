package com.example.alarmoffsetapp.data

import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDateTime

data class Alarm(
    val id: Int,
    val name: String,
    val nextTime: LocalDateTime,
    val daysOfWeek: Set<DayOfWeek>,
    val isVibrating: Boolean,
    val alarmOffsets: List<AlarmOffset>,
    val isActive: Boolean,
    val canSnoozeOffsets: Boolean
)


