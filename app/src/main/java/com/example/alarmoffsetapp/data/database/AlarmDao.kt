package com.example.alarmoffsetapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.alarmoffsetapp.data.database.data.AlarmEntity
import com.example.alarmoffsetapp.data.database.data.AlarmGroupWithAlarms
import com.example.alarmoffsetapp.data.database.data.AlarmOffsetEntity
import com.example.alarmoffsetapp.data.database.data.AlarmWithOffsets
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {
    @Insert
    suspend fun insertAlarm(alarm: AlarmEntity): Long

    @Insert
    suspend fun insertAlarmOffsets(offsets: List<AlarmOffsetEntity>)

    @Query("SELECT * FROM alarms WHERE id = :id")
    fun getAlarmWithOffsets(id: Long): Flow<AlarmWithOffsets>

    @Query("SELECT * FROM alarms ORDER BY time ASC")
    fun getAllAlarmsWithOffsets(): Flow<List<AlarmWithOffsets>>

    @Query("SELECT * FROM alarm_groups ORDER BY name ASC")
    fun getAllAlarmGroupsWithAlarms(): Flow<List<AlarmGroupWithAlarms>>
}