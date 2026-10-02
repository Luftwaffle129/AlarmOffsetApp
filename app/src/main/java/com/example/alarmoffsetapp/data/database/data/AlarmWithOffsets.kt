package com.example.alarmoffsetapp.data.database.data

import androidx.room.Embedded
import androidx.room.Relation
import com.example.alarmoffsetapp.data.Alarm
import java.time.LocalDate
import java.time.LocalTime

data class AlarmWithOffsets (
    @Embedded val alarm: AlarmEntity,
    @Relation(
        entity = AlarmOffsetEntity::class,
        parentColumn = "id",
        entityColumn = "alarm_id"
    )
    val offsets: List<AlarmOffsetEntity>
) {
    fun toAlarm() : Alarm {
        return Alarm(
            id = alarm.id,
            name = alarm.name,
            baseTime = LocalTime.ofSecondOfDay(alarm.baseTime.toLong()),
            isRepeating = alarm.isRepeating,
            scheduledDate = if (alarm.scheduledDate == null) null else LocalDate.ofEpochDay(alarm.scheduledDate),
            daysOfWeek = alarm.daysOfWeek.toDaysOfWeekSet(),
            isVibrating = alarm.isVibrating,
            playsSound = alarm.playsSound,
            isActive = alarm.isActive,
            canDismissOffsets = alarm.canDismissOffsets,
            alarmGroup = null,                                              // TODO: placeholder
            alarmOffsets = offsets.map { it.toAlarmOffset() }
        )
    }
}