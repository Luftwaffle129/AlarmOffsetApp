package com.example.alarmoffsetapp.ui.EditAlarm

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alarmoffsetapp.ui.components.DayPeriodWheelPicker
import com.example.alarmoffsetapp.ui.components.TimeWheelPicker
import com.example.alarmoffsetapp.ui.theme.AppTheme
import java.time.DayOfWeek

@Composable
fun EditAlarmScreen(
    is24HourFormat: Boolean,
    modifier: Modifier = Modifier,
    viewModel: EditAlarmViewModel = EditAlarmViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    EditAlarmBody(
        uiState = uiState.value,
        is24HourFormat = is24HourFormat,
        onHourSelected = {},
        onMinuteSelected = {},
        onTimePeriodSelected = {},
        modifier = modifier
    )
}

@Composable
fun EditAlarmBody(
    uiState: EditAlarmScreenUiState,
    is24HourFormat: Boolean,
    onHourSelected: (Int) -> Unit,
    onMinuteSelected: (Int) -> Unit,
    onTimePeriodSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            TimePicker(
                startHour = uiState.hour,
                startMinute = uiState.minute,
                is24HourFormat = is24HourFormat,
                onHourSelected = onHourSelected,
                onMinuteSelected = onMinuteSelected,
                onTimePeriodSelected = onTimePeriodSelected
            )
        }
    }
}

@Composable
fun TimePicker(
    startHour: Int,
    startMinute: Int,
    is24HourFormat: Boolean,
    onHourSelected: (Int) -> Unit,
    onMinuteSelected: (Int) -> Unit,
    onTimePeriodSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val middleIndex = Int.MAX_VALUE / 2

    val maxHours = if (is24HourFormat) 24 else 12

    // remember list states between recompositions
    val hourListState = rememberLazyListState(
        initialFirstVisibleItemIndex = middleIndex - (middleIndex % maxHours) + startHour
    )
    val minuteListState = rememberLazyListState(
        initialFirstVisibleItemIndex = middleIndex - (middleIndex % 60) + startMinute
    )
    val dayPeriodListState = rememberLazyListState(
        initialFirstVisibleItemIndex = if (startHour in 12..23) 1 else 0
    )

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        TimeWheelPicker(
            maxValue = 24,
            listState = hourListState,
            onSelected = onHourSelected,
            isTwoDigits = false,
            is12HourFormat = true
        )
        Text(
            text = ":",
            style = MaterialTheme.typography.displayLarge,
            fontSize = 64.sp
        )
        TimeWheelPicker(
            maxValue = 60,
            listState = minuteListState,
            onSelected = onMinuteSelected,
        )
        if (!is24HourFormat) {
            DayPeriodWheelPicker(
                listState = dayPeriodListState,
                onSelected = onTimePeriodSelected
            )
        }
    }
}

@Composable
fun IntervalSelector(
    daysOfWeek: Set<DayOfWeek>,
    onCalendarClicked: () -> Unit,
    onDayOfWeekCheckedChange: (DayOfWeek, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "test")
            IconButton(
                onClick = onCalendarClicked
            ) {
                Icon(
                    imageVector = Icons.Outlined.CalendarMonth,
                    contentDescription = null,
                    modifier = modifier.size(24.dp)
                )
            }
        }
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items(7) { value ->
                val dayOfWeek = DayOfWeek.of(value + 1)

                IconToggleButton(
                    checked = daysOfWeek.contains(dayOfWeek),
                    onCheckedChange = {  onDayOfWeekCheckedChange(dayOfWeek, it) }
                ) {
                    Text(
                        text = DayOfWeek.of(value + 1).name.substring(0..0),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 24.sp,
                    )
                }

            }
        }
    }
}
@Preview
@Composable
fun IntervalSelectorLightPreview() {
    AppTheme {
        Surface(
            modifier = Modifier,
            color = MaterialTheme.colorScheme.background
        ) {
            IntervalSelector(
                daysOfWeek = setOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
                onDayOfWeekCheckedChange = { dayOfWeek, isChecked -> },
                onCalendarClicked = {},
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun TimePickerLightPreview() {
    AppTheme {
        Surface(
            modifier = Modifier,
            color = MaterialTheme.colorScheme.background
        ) {
            Box {
                TimePicker(
                    startHour = 2,
                    startMinute = 2,
                    is24HourFormat = false,
                    onHourSelected = {},
                    onMinuteSelected = {},
                    onTimePeriodSelected = {}
                )
            }
        }
    }
}