package com.example.alarmoffsetapp.data.database

import androidx.room.Embedded
import androidx.room.Relation

data class AlarmWithOffsets (
    @Embedded val alarm: AlarmEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "alarm_id"
    )
    val offsets: List<AlarmOffsetEntity>
)