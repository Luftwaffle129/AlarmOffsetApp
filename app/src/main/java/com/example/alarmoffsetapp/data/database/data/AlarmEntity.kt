package com.example.alarmoffsetapp.data.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.DayOfWeek

@Entity(tableName = "alarms")
data class AlarmEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "group_id") val groupId: Long?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "time") val baseTime: Int, // seconds since midnight
    @ColumnInfo(name = "is_repeating") val isRepeating: Boolean,
    @ColumnInfo(name = "next_time")  val scheduledDate: Long?, // date of alarm
    @ColumnInfo(name = "days_of_week")  val daysOfWeek: Byte, // bitmask of days of week
    @ColumnInfo(name = "is_vibrating") val isVibrating: Boolean,
    @ColumnInfo(name = "plays_sound") val playsSound: Boolean,
    @ColumnInfo(name = "is_active") val isActive: Boolean,
    @ColumnInfo(name = "can_snooze_offsets")  val canDismissOffsets: Boolean
)

fun Set<DayOfWeek>.toByte() : Byte {
    var value = 0
    for (day in this) {
        value = value or (1 shl (day.value - 1))
    }
    return value.toByte()
}

fun Byte.toDaysOfWeekSet() : Set<DayOfWeek> {
    val set = mutableSetOf<DayOfWeek>()
    for (i in 1..7) {
        val mask = 1 shl (i - 1)
        if ((this.toInt() and mask) != 0) {
            set.add(DayOfWeek.of(i))
        }
    }
    return set
}