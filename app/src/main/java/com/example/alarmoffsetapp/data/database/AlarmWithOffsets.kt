package com.example.alarmoffsetapp.data.database

import androidx.room.Embedded
import androidx.room.Relation
import com.example.alarmoffsetapp.data.Alarm

data class AlarmWithOffsets (
    @Embedded val alarm: Alarm,
    @Relation(
        parentColumn = "id",
        entityColumn = "alarm_id"
    )
    val offsets: List<AlarmOffsetEntity>
)