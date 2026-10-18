package com.example.alarmoffsetapp.fake

import com.example.alarmoffsetapp.data.Alarm
import com.example.alarmoffsetapp.data.AlarmGroup
import com.example.alarmoffsetapp.data.AlarmOffset
import com.example.alarmoffsetapp.data.database.AlarmsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeAlarmRepository(
    private val alarms: MutableStateFlow<List<Alarm>> = MutableStateFlow(FakeDataSource().alarmList),
    private val alarmGroups: MutableStateFlow<List<AlarmGroup>> = MutableStateFlow(FakeDataSource().alarmGroupList)
) : AlarmsRepository {

    override suspend fun insertAlarm(alarm: Alarm): Long {
        val updated = alarms.value.toMutableList()
        val index = updated.indexOfFirst { it.id == alarm.id }

        if (index != -1) {
            updated[index] = alarm
        } else {
            updated.add(alarm)
        }

        alarms.value = updated
        return alarm.id
    }

    override suspend fun insertAlarmOffsets(offsets: List<AlarmOffset>): List<Long> {
        TODO("Not yet implemented")
    }

    override fun getAlarmStream(id: Long): Flow<Alarm> = alarms.map { alarms -> alarms.find { it.id == id } ?: throw Exception("Alarm not found")}

    override fun getAllAlarmsStream(): Flow<List<Alarm>> = alarms

    override fun getAllAlarmGroupsStream(): Flow<List<AlarmGroup>> = alarmGroups

    companion object {
        fun empty() = FakeAlarmRepository(alarms = MutableStateFlow(emptyList()), alarmGroups = MutableStateFlow(emptyList()))
    }
}
