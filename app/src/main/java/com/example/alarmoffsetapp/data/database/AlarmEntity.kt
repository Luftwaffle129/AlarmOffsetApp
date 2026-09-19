package com.example.alarmoffsetapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarms")
data class AlarmEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "group_id") val groupId: Long?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "time") val time: Int, // seconds since midnight
    @ColumnInfo(name = "is_repeating") val isRepeating: Boolean,
    @ColumnInfo(name = "next_time")  val nextTime: String?, // date of alarm
    @ColumnInfo(name = "days_of_week")  val daysOfWeek: Byte?, // bitmask of days of week
    @ColumnInfo(name = "is_vibrating") val isVibrating: Boolean,
    @ColumnInfo(name = "is_active") val isActive: Boolean,
    @ColumnInfo(name = "can_snooze_offsets")  val canSnoozeOffsets: Boolean
)