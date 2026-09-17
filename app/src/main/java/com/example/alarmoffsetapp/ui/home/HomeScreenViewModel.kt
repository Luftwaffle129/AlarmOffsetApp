package com.example.alarmoffsetapp.ui.home

import androidx.lifecycle.ViewModel
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

    fun AlarmAsString(): Duration {
        return Duration.between(uiState.value.nextAlarm, LocalDateTime.now())
    }
}

data class HomeScreenUiState(
    val nextAlarm: LocalDateTime? = null,
    val timeUntilNextAlarm: Duration? = null
)