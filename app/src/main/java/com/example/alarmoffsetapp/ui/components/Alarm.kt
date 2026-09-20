package com.example.alarmoffsetapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BrowseGallery
import androidx.compose.material3.Surface
import androidx.compose.ui.res.dimensionResource
import com.example.alarmoffsetapp.data.AlarmOffset
import com.example.alarmoffsetapp.preview.SampleData
import com.example.alarmoffsetapp.ui.theme.Shapes
import com.example.alarmoffsetapp.ui.util.dateTimeToTimeString
import com.example.alarmoffsetapp.ui.util.getAmOrPm
import java.time.Duration

@Composable
fun AlarmList(
    alarms: List<Alarm>,
    onAlarmClick: (Alarm) -> Unit,
    onAlarmToggle: (Alarm) -> Unit,
    modifier: Modifier = Modifier,
    is24Hour: Boolean,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        alarms.forEach { alarm ->
            AlarmCard(
                alarm = alarm,
                onClick = onAlarmClick,
                onToggle = onAlarmToggle,
                is24Hour = is24Hour
            )
        }
    }
}

@Composable
fun AlarmCard(
    onClick: (Alarm) -> Unit,
    onToggle: (Alarm) -> Unit,
    alarm: Alarm,
    modifier: Modifier = Modifier,
    is24Hour: Boolean,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable() { onClick(alarm) },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = if (alarm.isActive) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(
                start = dimensionResource(R.dimen.padding_medium),
                end = dimensionResource(R.dimen.padding_medium),
                top = dimensionResource(R.dimen.padding_small),
                bottom = dimensionResource(R.dimen.padding_small)
            )
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
                    modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_medium)),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = buildAnnotatedString {
                        append(dateTimeToTimeString(alarm.getNextAlarm(), is24Hour))
                        if (!is24Hour) withStyle(
                            SpanStyle(
                                fontSize = 28.sp
                            )
                        ) {
                            append("\u200A")
                            append(getAmOrPm(alarm.getNextAlarm()))
                        }
                    },
                    style = MaterialTheme.typography.displayLarge
                )
                Text (
                    text = dateTimeToDateString(alarm.getNextAlarm()),
                    modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_medium)),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (alarm.alarmOffsets.isNotEmpty()) {
                    Column() {
                        Surface(
                            modifier = Modifier
                                .padding(start = dimensionResource(R.dimen.padding_medium)),
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.secondaryContainer,
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.BrowseGallery,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                        .size(32.dp),
                                    tint = if (alarm.isActive) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                                )

                                Text(
                                    text = alarm.alarmOffsets.size.toString(),
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontSize = 28.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.SpaceBetween
//                        ) {
//                            Icon(
//                                imageVector = Icons.Outlined.BrowseGallery,
//                                contentDescription = null,
//                                modifier = Modifier.padding().size(32.dp),
//                                tint = if (alarm.isActive) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
//                            )
//                            Text(
//                                text = alarm.alarmOffsets.size.toString(),
//                                modifier = Modifier,
//                                style = MaterialTheme.typography.bodyMedium,
//                                fontSize = 28.sp,
//                                textAlign = TextAlign.Center
//                            )
//                        }
                    }
                }

                Switch(
                    checked = alarm.isActive,
                    onCheckedChange = { onToggle(alarm.copy(isActive = !alarm.isActive)) },
                    modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_medium)),
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
        val alarmOffset = AlarmOffset(offset = Duration.ZERO, isActive = true)

        AlarmCard(
            alarm = SampleData.alarm,
            onClick = {},
            onToggle = {},
            is24Hour = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AlarmListPreviewLight() {
    AppTheme(dynamicColor = false, darkTheme = false) {
        AlarmList(
            alarms = SampleData.alarms,
            onAlarmClick = {},
            onAlarmToggle = {},
            is24Hour = false
        )
    }
}

@Preview
@Composable
fun AlarmCardPreviewDark() {
    val alarmOffset = AlarmOffset(offset = Duration.ZERO, isActive = true)
    AppTheme(dynamicColor = false, darkTheme = true) {
        AlarmCard(
            alarm = SampleData.alarm,
            onClick = {},
            onToggle = {},
            is24Hour = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AlarmListPreviewDark() {
    AppTheme(dynamicColor = false, darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            AlarmList(
                alarms = SampleData.alarms,
                onAlarmClick = {},
                onAlarmToggle = {},
                is24Hour = false
            )
        }

    }
}