package com.example.alarmoffsetapp.data.database

import kotlinx.coroutines.flow.Flow

class OfflineAlarmsRepository(private val alarmDao: AlarmDao) : AlarmsRepository {
    override suspend fun insertAlarm(alarm: AlarmEntity): Long = alarmDao.insertAlarm(alarm)

    override suspend fun insertAlarmOffsets(offsets: List<AlarmOffsetEntity>) = alarmDao.insertAlarmOffsets(offsets)

    override fun getAllAlarms(): Flow<List<AlarmWithOffsets>> = alarmDao.getAllAlarms()

    override fun getAllAlarmGroups(): Flow<List<AlarmGroupWithAlarms>> = alarmDao.getAllAlarmGroups()
}