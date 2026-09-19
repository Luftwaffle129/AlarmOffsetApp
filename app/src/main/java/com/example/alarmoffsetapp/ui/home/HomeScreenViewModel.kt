package com.example.alarmoffsetapp.ui.home

import androidx.lifecycle.ViewModel
import com.example.alarmoffsetapp.data.Alarm
import com.example.alarmoffsetapp.data.AlarmGroup
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime
import java.time.Duration

class HomeScreenViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    fun getTimeUntilNextAlarm(): Duration {
        return Duration.between(uiState.value.nextAlarm, LocalDateTime.now())
    }

    fun onAddAlarm() {

    }

    fun onAlarmGroupClick(alarmGroup: AlarmGroup) {

    }

    fun onAlarmGroupToggle(alarmGroup: AlarmGroup) {

    }

    fun onAlarmClick(alarm: Alarm) {

    }

    fun onAlarmToggle(alarm: Alarm) {

    }
}

data class HomeScreenUiState(
    val nextAlarm: LocalDateTime? = null,
    val timeUntilNextAlarm: Duration? = null,
    val alarmGroups: List<AlarmGroup> = listOf(),
    val alarms: List<Alarm> = listOf()
)