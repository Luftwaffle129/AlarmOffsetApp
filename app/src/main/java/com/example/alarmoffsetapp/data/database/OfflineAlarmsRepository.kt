package com.example.alarmoffsetapp.data.database

import com.example.alarmoffsetapp.data.Alarm
import com.example.alarmoffsetapp.data.AlarmGroup
import com.example.alarmoffsetapp.data.AlarmOffset
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

class OfflineAlarmsRepository(private val alarmDao: AlarmDao) : AlarmsRepository {
    override suspend fun insertAlarm(alarm: AlarmEntity): Long = alarmDao.insertAlarm(alarm)

    override suspend fun insertAlarmOffsets(offsets: List<AlarmOffset>) {} // TODO: Implement this

    override fun getAlarmStream(id: Long): Flow<Alarm> = alarmDao.getAlarm(id).map {
        alarmWithOffsets -> alarmWithOffsets.toAlarm()
    }

    override fun getAllAlarmsStream(): Flow<List<Alarm>> = alarmDao.getAllAlarms().map { alarmWithOffsets ->
        alarmWithOffsets.map { alarmWithOffset ->
            alarmWithOffset.toAlarm()
        }
    }

    override fun getAllAlarmGroupsStream(): Flow<List<AlarmGroup>> = alarmDao.getAllAlarmGroups().map { alarmGroupWithAlarms ->
        alarmGroupWithAlarms.map {  alarmGroupWithAlarms ->
                alarmGroupWithAlarms.toAlarmGroup()
        }
    }
}
