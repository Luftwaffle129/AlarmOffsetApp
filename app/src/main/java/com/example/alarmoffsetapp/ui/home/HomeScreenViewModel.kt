package com.example.alarmoffsetapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarmoffsetapp.data.Alarm
import com.example.alarmoffsetapp.data.AlarmGroup
import com.example.alarmoffsetapp.data.database.AlarmsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.Duration
class HomeScreenViewModel(private val alarmsRepository: AlarmsRepository): ViewModel() {
    val uiState: StateFlow<HomeUiState> = combine(
        alarmsRepository.getAllAlarmsStream(),
        alarmsRepository.getAllAlarmGroupsStream()
    ) { alarms, groups ->
        val nextAlarmTime = alarms
            .filter { alarm -> alarm.isActive }
            .map { alarm -> alarm.getNextAlarm() }
            .minByOrNull { it }

        HomeUiState(
            alarms = alarms,
            alarmGroups = groups,
            nextAlarm = nextAlarmTime,
            timeUntilNextAlarm = getDurationUntilNextAlarm(nextAlarmTime)
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = HomeUiState()
        )

    fun getDurationUntilNextAlarm(time: LocalDateTime?): Duration? {
        return if (time != null) { // required as java Duration does not support nulls
            Duration.between(LocalDateTime.now(), time)
        } else {
            null
        }
    }

    fun onAlarmGroupToggle(alarmGroup: AlarmGroup) {

    }


    fun onAlarmToggle(alarm: Alarm) {
        viewModelScope.launch {
            alarmsRepository.insertAlarm(alarm.copy(isActive = !alarm.isActive))
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(
    val nextAlarm: LocalDateTime? = null,
    val timeUntilNextAlarm: Duration? = null,
    val alarmGroups: List<AlarmGroup> = listOf(),
    val alarms: List<Alarm> = listOf()
)