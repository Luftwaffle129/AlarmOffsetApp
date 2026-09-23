package com.example.alarmoffsetapp.data.database

import androidx.room.Embedded
import androidx.room.Relation
import com.example.alarmoffsetapp.data.AlarmGroup

data class AlarmGroupWithAlarms (
    @Embedded val group: AlarmGroupEntity,
    @Relation(
        entity = AlarmEntity::class,
        parentColumn = "id",
        entityColumn = "group_id"
    )
    val alarms: List<AlarmWithOffsets>
) {
    fun toAlarmGroup(): AlarmGroup {
        return AlarmGroup(
            id = group.id,
            name = group.name,
            isActive = group.isActive,
            alarms = alarms.map { it.toAlarm() }
        )
    }
}