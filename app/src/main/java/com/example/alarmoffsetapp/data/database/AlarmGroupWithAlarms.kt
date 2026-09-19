package com.example.alarmoffsetapp.data.database

import androidx.room.Embedded
import androidx.room.Relation

data class AlarmGroupWithAlarms (
    @Embedded val group: AlarmGroupEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "group_id"
    )
    val alarms: List<AlarmEntity>
)