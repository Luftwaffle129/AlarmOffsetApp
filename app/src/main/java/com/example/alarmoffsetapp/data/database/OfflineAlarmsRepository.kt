package com.example.alarmoffsetapp.data.database

import com.example.alarmoffsetapp.data.Alarm
import com.example.alarmoffsetapp.data.AlarmGroup
import com.example.alarmoffsetapp.data.AlarmOffset
import com.example.alarmoffsetapp.data.database.data.AlarmEntity
import com.example.alarmoffsetapp.data.database.data.toByte
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflineAlarmsRepository(private val alarmDao: AlarmDao) : AlarmsRepository {
    override suspend fun insertAlarm(alarm: Alarm): Long {
        return alarmDao.insertAlarm(
            AlarmEntity(
                id = alarm.id,
                groupId = alarm.alarmGroup?.id,
                name = alarm.name,
                baseTime = alarm.baseTime.toSecondOfDay(),
                isRepeating = alarm.isRepeating,
                scheduledDate = alarm.scheduledDate?.toEpochDay(),
                daysOfWeek = alarm.daysOfWeek.toByte(),
                isVibrating = alarm.isVibrating,
                playsSound = alarm.playsSound,
                isActive = alarm.isActive,
                canDismissOffsets = alarm.canDismissOffsets
            )
        )
    }

    override suspend fun insertAlarmOffsets(offsets: List<AlarmOffset>) {} // TODO: Implement this

    override fun getAlarmStream(id: Long): Flow<Alarm> = alarmDao.getAlarmWithOffsets(id).map {
        alarmWithOffsets -> alarmWithOffsets.toAlarm()
    }

    override fun getAllAlarmsStream(): Flow<List<Alarm>> = alarmDao.getAllAlarmsWithOffsets().map { alarmWithOffsets ->
        alarmWithOffsets.map { alarmWithOffset ->
            alarmWithOffset.toAlarm()
        }
    }

    override fun getAllAlarmGroupsStream(): Flow<List<AlarmGroup>> = alarmDao.getAllAlarmGroupsWithAlarms().map { alarmGroupWithAlarms ->
        alarmGroupWithAlarms.map {  alarmGroupWithAlarms ->
                alarmGroupWithAlarms.toAlarmGroup()
        }
    }
}
