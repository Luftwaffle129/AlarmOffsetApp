package com.example.alarmoffsetapp.data

import java.time.Duration

data class AlarmOffset(
    val id: Long,
    val offset: Duration,
    val isActive: Boolean
)
