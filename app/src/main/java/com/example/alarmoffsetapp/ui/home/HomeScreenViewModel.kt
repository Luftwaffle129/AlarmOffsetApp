package com.example.alarmoffsetapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarmoffsetapp.data.Alarm
import com.example.alarmoffsetapp.data.AlarmGroup
import com.example.alarmoffsetapp.data.database.AlarmsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDateTime
import java.time.Duration
class HomeScreenViewModel(private val alarmsRepository: AlarmsRepository): ViewModel() {
    val uiState: StateFlow<HomeUiState> = combine(
        alarmsRepository.getAllAlarmsStream(),
        alarmsRepository.getAllAlarmGroupsStream(),
    ) { alarms, groups ->
        HomeUiState(
            alarms = alarms,
            alarmGroups = groups,
            nextAlarm = alarms.map { alarm ->
                alarm.getNextAlarm()
            }.minByOrNull { it },
            timeUntilNextAlarm = getTimeUntilNextAlarm()
        )

    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUiState()
        )

    fun getTimeUntilNextAlarm(): Duration {
        return Duration.between(uiState.value.nextAlarm, LocalDateTime.now())
    }

    fun onAlarmGroupToggle(alarmGroup: AlarmGroup) {

    }


    fun onAlarmToggle(alarm: Alarm) {

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