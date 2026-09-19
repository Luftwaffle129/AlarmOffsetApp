package com.example.alarmoffsetapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm_offsets")
data class AlarmOffsetEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "alarm_id") val alarmId: Long,
    @ColumnInfo(name = "offset") val offset: Int, // limited to up to a day (86400 seconds)
    @ColumnInfo(name = "isActive")val isActive: Boolean
)
