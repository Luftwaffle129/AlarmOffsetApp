package com.example.alarmoffsetapp.ui.EditAlarm

import android.R.attr.alpha
import android.R.attr.scaleX
import android.R.attr.scaleY
import android.annotation.SuppressLint
import android.widget.TimePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import androidx.compose.ui.unit.sp
import com.example.alarmoffsetapp.data.Alarm
import com.example.alarmoffsetapp.preview.SampleData.alarm
import com.example.alarmoffsetapp.ui.components.DayPeriodWheelPicker
import com.example.alarmoffsetapp.ui.components.TimeWheelPicker
import com.example.alarmoffsetapp.ui.theme.AppTheme
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import java.util.Locale
import kotlin.math.abs

@Composable
fun EditAlarmScreen(
    is24HourFormat: Boolean,
    modifier: Modifier = Modifier,
) {
    EditAlarmBody(
        is24HourFormat = is24HourFormat,
        modifier = modifier
    )
}

@Composable
fun EditAlarmBody(
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
                startHour = 2,
                startMinute = 2,
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