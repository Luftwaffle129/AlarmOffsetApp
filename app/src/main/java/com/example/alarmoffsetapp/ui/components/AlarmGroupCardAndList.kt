package com.example.alarmoffsetapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AlarmOff
import androidx.compose.material.icons.outlined.AlarmOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alarmoffsetapp.R
import com.example.alarmoffsetapp.data.AlarmGroup
import com.example.alarmoffsetapp.preview.SampleData
import com.example.alarmoffsetapp.ui.theme.AppTheme

/**
 * Displays a list of alarm groups.
 *
 * @param alarmGroups the list of alarm groups to display
 * @param onAlarmGroupClick the callback to invoke when an alarm group is clicked
 * @param onAlarmGroupToggle the callback to invoke when the toggle for an alarm group is clicked
 * @param modifier the modifier to apply to this layout
 */
@Composable
fun AlarmGroupList(
    alarmGroups: List<AlarmGroup>,
    onAlarmGroupClick: (AlarmGroup) -> Unit,
    onAlarmGroupToggle: (AlarmGroup) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        alarmGroups.forEach { alarmGroup ->
            AlarmGroupCard(
                alarmGroup = alarmGroup,
                onClick = onAlarmGroupClick,
                onToggle = onAlarmGroupToggle
            )
        }
    }
}
/**
 * Displays a single alarm group.
 *
 * @param alarmGroup the alarm group to display
 * @param onClick the callback to invoke when this alarm group is clicked
 * @param onToggle the callback to invoke when the toggle for this alarm group is clicked
 * @param modifier the modifier to apply to this layout
 */
@Composable
fun AlarmGroupCard(
    alarmGroup: AlarmGroup,
    onClick: (AlarmGroup) -> Unit,
    onToggle: (AlarmGroup) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable() { onClick(alarmGroup) },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = if (alarmGroup.isActive) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium),
                    top = dimensionResource(R.dimen.padding_small),
                    bottom = dimensionResource(R.dimen.padding_small)
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = alarmGroup.name,
                modifier = Modifier.padding(start = 8.dp),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 28.sp,
                textAlign = TextAlign.Center
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // active and inactive alarms

                Icon(
                    imageVector = Icons.Outlined.AlarmOn,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(R.dimen.padding_medium),
                            end = dimensionResource(R.dimen.padding_extra_small)
                        )
                        .size(28.dp),
                    tint = if (alarmGroup.isActive) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = alarmGroup.getActiveAlarms().toString(),
                    modifier = Modifier,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
                Icon(
                    imageVector = Icons.Outlined.AlarmOff,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(R.dimen.padding_medium),
                            end = dimensionResource(R.dimen.padding_extra_small)
                        )
                        .size(28.dp),
                    tint = if (alarmGroup.isActive) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = alarmGroup.getActiveAlarms().toString(),
                    modifier = Modifier,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )

                Switch(
                    checked = alarmGroup.isActive,
                    onCheckedChange = { onToggle(alarmGroup.copy(isActive = !alarmGroup.isActive)) },
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
fun AlarmGroupCardPreviewLight() {

    AppTheme(dynamicColor = false, darkTheme = false) {
        AlarmGroupCard(
            alarmGroup = SampleData.alarmGroup,
            onClick = {},
            onToggle = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AlarmGroupListPreviewLight() {
    AppTheme(dynamicColor = false, darkTheme = false) {
        AlarmGroupList(
            alarmGroups = SampleData.alarmGroups,
            onAlarmGroupClick = {},
            onAlarmGroupToggle = {}
        )
    }
}

@Preview
@Composable
fun AlarmGroupCardPreviewDark() {

    AppTheme(dynamicColor = false, darkTheme = true) {
        AlarmGroupCard(
            alarmGroup = SampleData.alarmGroup,
            onClick = {},
            onToggle = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AlarmGroupListPreviewDark() {
    AppTheme(dynamicColor = false, darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            AlarmGroupList(
                alarmGroups = SampleData.alarmGroups,
                onAlarmGroupClick = {},
                onAlarmGroupToggle = {}
            )
        }

    }
}