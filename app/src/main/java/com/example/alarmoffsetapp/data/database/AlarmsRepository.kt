package com.example.alarmoffsetapp.data.database

import com.example.alarmoffsetapp.data.Alarm
import com.example.alarmoffsetapp.data.AlarmGroup
import com.example.alarmoffsetapp.data.AlarmOffset
import kotlinx.coroutines.flow.Flow

interface AlarmsRepository {

    suspend fun insertAlarm(alarm: AlarmEntity): Long

    suspend fun insertAlarmOffsets(offsets: List<AlarmOffset>)

    fun getAlarmStream(id: Long): Flow<Alarm>
    fun getAllAlarmsStream(): Flow<List<Alarm>>

    fun getAllAlarmGroupsStream(): Flow<List<AlarmGroup>>
}