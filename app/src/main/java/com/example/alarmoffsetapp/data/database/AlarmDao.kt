package com.example.alarmoffsetapp.data.database

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface AlarmDao {
    @Insert
    suspend fun insertAlarm(alarm: AlarmEntity): Long
}