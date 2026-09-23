package com.example.alarmoffsetapp.ui.editAlarm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarmoffsetapp.data.Alarm
import com.example.alarmoffsetapp.data.database.AlarmsRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate

class EditAlarmViewModel(
    savedStateHandle: SavedStateHandle,
    private val alarmsRepository: AlarmsRepository
) : ViewModel() {

 mutableStateOf(EditAlarmUiState())
        private set

    private val alarmId: Long? = savedStateHandle[EditAlarmDestination.alarmIdArg]
    var uiState by

    init {
        viewModelScope.launch {
            uiState =
                .toItemUiState()
        }
    }
}

data class EditAlarmUiState(
    val alarm: Alarm? = null,
    val hour: Int = 0,
    val minute: Int = 0,
    val alarmInterval: String = "Tomorrow",
    val daysOfWeek: Set<DayOfWeek> = setOf(),
    val calendarDay: LocalDate? = LocalDate.now().plusDays(1),
    val name: String = "",
    val isVibrating: Boolean = true,
)