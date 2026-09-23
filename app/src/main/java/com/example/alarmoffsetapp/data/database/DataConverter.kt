package com.example.alarmoffsetapp.data.database

import java.time.DayOfWeek

object DataConverter {
    fun daysOfWeekSetToByte(daysOfWeek: Set<DayOfWeek>) : Byte {
        var value = 0
        for (day in daysOfWeek) {
            value += Math.powExact(2, day.value).toByte()
        }
        return value.toByte()
    }

    fun daysOfWeekByteToSet(daysOfWeek: Byte) : Set<DayOfWeek> {
        val set: Set<DayOfWeek> = mutableSetOf()
        for (i in 1..7) {
            if (i and daysOfWeek.toInt() != 0) set.plus(DayOfWeek.of(i))
        }
        return set
    }
}