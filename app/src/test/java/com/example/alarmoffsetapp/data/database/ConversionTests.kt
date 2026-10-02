package com.example.alarmoffsetapp.data.database


import com.example.alarmoffsetapp.data.database.data.toByte
import com.example.alarmoffsetapp.data.database.data.toDaysOfWeekSet
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.DayOfWeek

class ConversionTests {
    @Test
    fun daysOfWeekSetToByte_isCorrect() {
        val set = setOf<DayOfWeek>(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY)
        val byte = set.toByte()
        assertEquals(5.toByte(),byte)
    }

    @Test
    fun byteToDaysOfWeekSet_isCorrect() {
        val byte: Byte = 5
        val set = byte.toDaysOfWeekSet()
        assertEquals(setOf<DayOfWeek>(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),set)
    }

    @Test
    fun byteToDaysOfWeekSetThenToByte_isUnchanged() {
        val byte: Byte = 86
        val newByte = byte.toDaysOfWeekSet().toByte()
        assertEquals(byte,newByte)
    }
}