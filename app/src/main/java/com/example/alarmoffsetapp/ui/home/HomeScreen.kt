package com.example.alarmoffsetapp.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alarmoffsetapp.ui.AppViewModelProvider
import java.time.Duration
import java.time.LocalDateTime
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alarmoffsetapp.R
import com.example.alarmoffsetapp.data.Alarm
import com.example.alarmoffsetapp.data.AlarmGroup
import com.example.alarmoffsetapp.ui.components.AlarmGroupList
import com.example.alarmoffsetapp.ui.components.AlarmList
import com.example.alarmoffsetapp.ui.theme.AppTheme
import com.example.alarmoffsetapp.ui.util.durationToString

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState.collectAsState()

    HomeBody(
        uiState = uiState.value,
        onAlarmClick = viewModel::onAlarmClick,
        onAlarmToggle = viewModel::onAlarmToggle,
        onAlarmGroupClick = viewModel::onAlarmGroupClick,
        onAlarmGroupToggle = viewModel::onAlarmGroupToggle,
        modifier = modifier
    )
}

@Composable
fun HomeBody(
    uiState: HomeScreenUiState,
    onAlarmClick: (Alarm) -> Unit,
    onAlarmToggle: (Alarm) -> Unit,
    onAlarmGroupClick: (AlarmGroup) -> Unit,
    onAlarmGroupToggle: (AlarmGroup) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item{
            Text(
                text = stringResource(R.string.next_alarm),
                style = MaterialTheme.typography.labelLarge,
            )
        }
        item {
            NextAlarm(
                nextAlarm = uiState.nextAlarm,
                timeUntilNextAlarm = uiState.timeUntilNextAlarm
            )
        }
        item {
            Text(
                text = stringResource(R.string.alarm_groups),
                style = MaterialTheme.typography.labelLarge,
            )
        }
        item {
            AlarmGroupList(
                alarmGroups = uiState.alarmGroups,
                onAlarmGroupClick = onAlarmGroupClick,
                onAlarmGroupToggle = onAlarmGroupToggle
            )
        }
        item {
            Text(
                text = stringResource(R.string.alarms),
                style = MaterialTheme.typography.labelLarge,
            )
        }
        item {
            AlarmList(
                alarms = uiState.alarms,
                onAlarmClick = onAlarmClick,
                onAlarmToggle = onAlarmToggle,
                is24Hour = true                                 // TODO: handle 24 hour
            )
        }
    }
}

@Composable
fun NextAlarm(
    nextAlarm: LocalDateTime?,
    timeUntilNextAlarm: Duration?,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .height(128.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        if (nextAlarm == null || timeUntilNextAlarm == null) {
            Box(
                modifier = Modifier.padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.no_alarms_set),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displaySmall
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // time until next alarm finishes
                Text(
                    text = durationToString(timeUntilNextAlarm), // placeholder
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(modifier.height(4.dp))
                // exact time of next alarm
                Text(
                    text = "23:07:01   September 3",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyLightPreview() {
    val alarm = Alarm(
        id = 0,
        name = "test",
        nextTime = LocalDateTime.now(),
        daysOfWeek = setOf(),
        isVibrating = true,
        alarmOffsets = listOf(),
        isActive = false,
        canSnoozeOffsets = true
    )
    val alarms = listOf(alarm, alarm.copy(isActive = true), alarm)
    val alarmGroup = AlarmGroup(
        id = 0,
        name = "test",
        alarms = alarms,
        isActive = false,
    )
    val alarmGroups = listOf(alarmGroup, alarmGroup.copy(isActive = true), alarmGroup)

    val uiState = HomeScreenUiState(
        nextAlarm = LocalDateTime.now(),
        timeUntilNextAlarm = Duration.ofDays(1),
        alarmGroups = alarmGroups,
        alarms = alarms
    )

    AppTheme(dynamicColor = false, darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            HomeBody(
                uiState = uiState,
                onAlarmClick = {},
                onAlarmToggle = {},
                onAlarmGroupClick = {},
                onAlarmGroupToggle = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyDarkPreview() {
    val alarm = Alarm(
        id = 0,
        name = "test",
        nextTime = LocalDateTime.now(),
        daysOfWeek = setOf(),
        isVibrating = true,
        alarmOffsets = listOf(),
        isActive = false,
        canSnoozeOffsets = true
    )
    val alarms = listOf(alarm, alarm.copy(isActive = true), alarm)
    val alarmGroup = AlarmGroup(
        id = 0,
        name = "test",
        alarms = alarms,
        isActive = false,
    )
    val alarmGroups = listOf(alarmGroup, alarmGroup.copy(isActive = true), alarmGroup)

    val uiState = HomeScreenUiState(
        nextAlarm = LocalDateTime.now(),
        timeUntilNextAlarm = Duration.ofDays(1),
        alarmGroups = alarmGroups,
        alarms = alarms
    )

    AppTheme(dynamicColor = false, darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            HomeBody(
                uiState = uiState,
                onAlarmClick = {},
                onAlarmToggle = {},
                onAlarmGroupClick = {},
                onAlarmGroupToggle = {}
            )
        }
    }
}

@Preview()
@Composable
fun NextAlarmPreview() {
    AppTheme(dynamicColor = false, darkTheme = false) {
        NextAlarm(
            nextAlarm = LocalDateTime.now(),
            timeUntilNextAlarm = Duration.ofDays(1)
        )
    }
}

@Preview()
@Composable
fun NextAlarmPreviewEmpty() {
    AppTheme(dynamicColor = false, darkTheme = false) {
        NextAlarm(
            nextAlarm = null,
            timeUntilNextAlarm = null
        )
    }
}
