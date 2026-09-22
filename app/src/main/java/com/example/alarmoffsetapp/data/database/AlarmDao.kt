package com.example.alarmoffsetapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {
    @Insert
    suspend fun insertAlarm(alarm: AlarmEntity): Long

    @Insert
    suspend fun insertAlarmOffsets(offsets: List<AlarmOffsetEntity>)

    @Query("SELECT * FROM alarms ORDER BY time ASC")
    fun getAllAlarms(): Flow<List<AlarmWithOffsets>>

    @Query("SELECT * FROM alarm_groups ORDER BY name ASC")
    fun getAllAlarmGroups(): Flow<List<AlarmGroupWithAlarms>>
}