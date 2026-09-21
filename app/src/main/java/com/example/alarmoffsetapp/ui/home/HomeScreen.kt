package com.example.alarmoffsetapp.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alarmoffsetapp.ui.AppViewModelProvider
import java.time.Duration
import java.time.LocalDateTime
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alarmoffsetapp.R
import com.example.alarmoffsetapp.data.Alarm
import com.example.alarmoffsetapp.data.AlarmGroup
import com.example.alarmoffsetapp.preview.SampleData
import com.example.alarmoffsetapp.ui.components.AlarmCard
import com.example.alarmoffsetapp.ui.components.AlarmGroupCard
import com.example.alarmoffsetapp.ui.components.AlarmGroupList
import com.example.alarmoffsetapp.ui.components.AlarmList
import com.example.alarmoffsetapp.ui.theme.AppTheme
import com.example.alarmoffsetapp.ui.util.dateTimeToString
import com.example.alarmoffsetapp.ui.util.durationToString

@Composable
fun HomeScreen(
    is24HourFormat: Boolean,
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState.collectAsState()

    HomeBody(
        uiState = uiState.value,
        is24HourFormat = is24HourFormat,
        onAddAlarm = viewModel::onAddAlarm,
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
    is24HourFormat: Boolean,
    onAddAlarm: () -> Unit,
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
            RegionTitle(
                title = R.string.next_alarm,
                icon = Icons.Outlined.MoreVert,
                onIconClick = {}                    // TODO: settings bar
            )
        }
        item {
            NextAlarm(
                nextAlarm = uiState.nextAlarm,
                is24HourFormat = is24HourFormat,
                timeUntilNextAlarm = uiState.timeUntilNextAlarm
            )
        }
        item {
            RegionTitle(
                title = R.string.alarm_groups,
                icon = null,
                onTextClick = {},                   // TODO: Open only alarm groups menu
            )
        }
        items(uiState.alarmGroups) { alarmGroup ->
            AlarmGroupCard(
                alarmGroup = alarmGroup,
                onClick = onAlarmGroupClick,
                onToggle = onAlarmGroupToggle
            )
        }
        item {
            RegionTitle(
                title = R.string.alarms,
                icon = Icons.Outlined.Add,
                onTextClick = {},                   // TODO: Open only alarm groups menu
                onIconClick = onAddAlarm
            )
        }
        items(uiState.alarms) { alarm ->
            AlarmCard(
                alarm = alarm,
                onClick = onAlarmClick,
                onToggle = onAlarmToggle,
                is24Hour = is24HourFormat
            )
        }
    }
}

@Composable
fun RegionTitle(
    @StringRes title: Int,
    icon: ImageVector?,
    onTextClick: () -> Unit = {},
    onIconClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(title),
            style = MaterialTheme.typography.labelLarge,
            fontSize = 20.sp
        )
        Row(
            modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_small)),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_small))
                )
            }
        }
    }
}

@Composable
fun NextAlarm(
    nextAlarm: LocalDateTime?,
    is24HourFormat: Boolean,
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
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
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
                    .padding(dimensionResource(R.dimen.padding_medium)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // time until next alarm finishes
                Text(
                    text = durationToString(timeUntilNextAlarm),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(modifier.height(dimensionResource(R.dimen.padding_small)))
                // exact time of next alarm
                Text(
                    text = dateTimeToString(nextAlarm, is24HourFormat),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyLightPreview() {

    AppTheme(dynamicColor = false, darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            HomeBody(
                uiState = SampleData.homeScreenUiState,
                is24HourFormat = false,
                onAddAlarm = {},
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
    AppTheme(dynamicColor = false, darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            HomeBody(
                uiState = SampleData.homeScreenUiState,
                is24HourFormat = false,
                onAddAlarm = {},
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
            is24HourFormat = false,
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
            is24HourFormat = false,
            timeUntilNextAlarm = null
        )
    }
}
