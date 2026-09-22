package com.example.alarmoffsetapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarmoffsetapp.data.Alarm
import com.example.alarmoffsetapp.data.AlarmGroup
import com.example.alarmoffsetapp.data.database.AlarmsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDateTime
import java.time.Duration

class HomeScreenViewModel(private val alarmsRepository: AlarmsRepository): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    val homeUiState: StateFlow<HomeUiState> = alarmsRepository.getAllItemsStream().map { HomeUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUiState()
        )

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

data class HomeUiState(
    val nextAlarm: LocalDateTime? = null,
    val timeUntilNextAlarm: Duration? = null,
    val alarmGroups: List<AlarmGroup> = listOf(),
    val individualAlarms: List<Alarm> = listOf()
)