package com.example.alarmoffsetapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alarmoffsetapp.R
import com.example.alarmoffsetapp.data.Alarm
import com.example.alarmoffsetapp.ui.theme.AppTheme
import com.example.alarmoffsetapp.ui.util.dateTimeToDateString
import java.time.LocalDateTime
import android.text.format.DateFormat
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.LocalContext
import com.example.alarmoffsetapp.ui.util.dateTimeToTimeString
import com.example.alarmoffsetapp.ui.util.getAmOrPm

@Composable
fun AlarmList(
    alarms: List<Alarm>,
    onAlarmClick: (Alarm) -> Unit,
    onAlarmToggle: (Alarm) -> Unit,
    modifier: Modifier = Modifier,
    is24Hour: Boolean,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surface
    ) {
        LazyColumn(
            modifier = modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(alarms) { alarm ->
                AlarmCard(
                    alarm = alarm,
                    onAlarmClick = onAlarmClick,
                    onAlarmToggle = onAlarmToggle,
                    is24Hour = is24Hour
                )
            }
        }
    }
}

@Composable
fun AlarmCard(
    onAlarmClick: (Alarm) -> Unit,
    onAlarmToggle: (Alarm) -> Unit,
    alarm: Alarm,
    modifier: Modifier = Modifier,
    is24Hour: Boolean,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable() { onAlarmClick(alarm) },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = if (alarm.isActive) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy((-8).dp)
            ) {
                Text(
                    text = alarm.name,
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = buildAnnotatedString {
                        append(dateTimeToTimeString(alarm.nextTime, is24Hour))
                        if (!is24Hour) withStyle(
                            SpanStyle(
                                fontSize = 28.sp
                            )
                        ) {
                            append("\u200A")
                            append(getAmOrPm(alarm.nextTime))
                        }
                    },
                    style = MaterialTheme.typography.displayLarge
                )
                Text (
                    text = dateTimeToDateString(alarm.nextTime),
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_browse_gallery_24),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 16.dp, end = 8.dp).size(32.dp),
                    tint = if (alarm.isActive) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = alarm.alarmOffsets.size.toString(),
                    modifier = Modifier,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center
                )

                Switch(
                    checked = alarm.isActive,
                    onCheckedChange = { onAlarmToggle(alarm.copy(isActive = !alarm.isActive)) },
                    modifier = Modifier.padding(start = 16.dp),
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.onSecondary,
                        checkedTrackColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun AlarmCardPreviewLight() {
    AppTheme(dynamicColor = false, darkTheme = false) {
        AlarmCard(
            alarm = Alarm(
                id = 0,
                name = "test",
                nextTime = LocalDateTime.now(),
                daysOfWeek = setOf(),
                isVibrating = true,
                alarmOffsets = listOf(),
                isActive = true,
                canSnoozeOffsets = true
            ),
            onAlarmClick = {},
            onAlarmToggle = {},
            is24Hour = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AlarmListPreviewLight() {
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

    AppTheme(dynamicColor = false, darkTheme = false) {
        AlarmList(
            alarms = alarms,
            onAlarmClick = {},
            onAlarmToggle = {},
            is24Hour = false
        )
    }
}

@Preview
@Composable
fun AlarmCardPreviewDark() {
    AppTheme(dynamicColor = false, darkTheme = true) {
        AlarmCard(
            alarm = Alarm(
                id = 0,
                name = "test",
                nextTime = LocalDateTime.now(),
                daysOfWeek = setOf(),
                isVibrating = true,
                alarmOffsets = listOf(),
                isActive = true,
                canSnoozeOffsets = true
            ),
            onAlarmClick = {},
            onAlarmToggle = {},
            is24Hour = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AlarmListPreviewDark() {
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

    AppTheme(dynamicColor = false, darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            AlarmList(
                alarms = alarms,
                onAlarmClick = {},
                onAlarmToggle = {},
                is24Hour = false
            )
        }

    }
}