package com.example.alarmoffsetapp.ui.EditAlarm

import androidx.lifecycle.ViewModel
import com.example.alarmoffsetapp.data.Alarm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.DayOfWeek
import java.time.LocalDate

class EditAlarmScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(EditAlarmScreenUiState())
    val uiState: StateFlow<EditAlarmScreenUiState> = _uiState.asStateFlow()
}

data class EditAlarmScreenUiState(
    val alarm: Alarm? = null,
    val hour: Int = 0,
    val minute: Int = 0,
    val alarmInterval: String = "Tomorrow",
    val daysOfWeek: Set<DayOfWeek> = setOf(),
    val calendarDay: LocalDate? = LocalDate.now().plusDays(1),
    val name: String = "",
    val isVibrating: Boolean = true,
)