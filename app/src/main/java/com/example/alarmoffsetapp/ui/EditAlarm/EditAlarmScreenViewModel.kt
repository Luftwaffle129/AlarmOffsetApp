package com.example.alarmoffsetapp.ui.EditAlarm

import androidx.lifecycle.ViewModel
import com.example.alarmoffsetapp.data.Alarm
import java.time.LocalDate

class EditAlarmScreenViewModel : ViewModel() {
}

data class EditAlarmScreenUiState(
    val alarm: Alarm?,
    val hour: Int,
    val minute: Int,
    val alarmInterval: String,
    val daysOfWeek: Set<DaysOfWeek>,
    val calendarDay: LocalDate?,
    val name: String,
    val isVibrating: Boolean,
)