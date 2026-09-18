package com.example.alarmoffsetapp.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.alarmoffsetapp.data.AlarmGroup
import com.example.alarmoffsetapp.ui.theme.AppTheme
import java.time.LocalDateTime

@Composable
fun AlarmGroups(
    alarmGroups: List<AlarmGroup>,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {

    }
}


@Preview()
@Composable
fun AlarmGroupsPreview() {
    AppTheme(dynamicColor = false, darkTheme = false) {
        AlarmGroups(alarmGroups = listOf())
    }
}