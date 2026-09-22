package com.example.alarmoffsetapp.data.database

import kotlinx.coroutines.flow.Flow

interface AlarmsRepository {

    suspend fun insertAlarm(alarm: AlarmEntity): Long

    suspend fun insertAlarmOffsets(offsets: List<AlarmOffsetEntity>)

    fun getAllAlarms(): Flow<List<AlarmWithOffsets>>

    fun getAllAlarmGroups(): Flow<List<AlarmGroupWithAlarms>>
}