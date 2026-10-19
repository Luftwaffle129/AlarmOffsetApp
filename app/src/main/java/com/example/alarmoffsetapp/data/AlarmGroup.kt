package com.example.alarmoffsetapp.data

data class AlarmGroup (
    val id: Long,
    val name: String,
    val alarms: List<Alarm>,
    val isActive: Boolean
) {
    fun getActiveAlarms(): Int {
        var count = 0
        for (alarm in alarms) {
            if (alarm.isActive) count++
        }
        return count
    }
}