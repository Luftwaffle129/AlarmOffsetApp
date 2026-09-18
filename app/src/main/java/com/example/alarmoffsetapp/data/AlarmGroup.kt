package com.example.alarmoffsetapp.data

data class AlarmGroup (
    val alarms: List<Alarm>,
) {
    fun getActiveAlarms(): Int {
        var count = 0
        for (alarm in alarms) {
            if (alarm.isActive) count++
        }
        return count
    }
}