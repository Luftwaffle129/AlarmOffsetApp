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
import com.example.alarmoffsetapp.ui.theme.AppTheme
import com.example.alarmoffsetapp.ui.util.durationToString

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = modifier
    ) {
        item {
            Text(
                text = stringResource(R.string.next_alarm),
                style = MaterialTheme.typography.labelLarge,
            )
            NextAlarm(
                nextAlarm = uiState.value.nextAlarm,
                timeUntilNextAlarm = uiState.value.timeUntilNextAlarm
            )
        }
        item {
            Text(
                text = stringResource(R.string.next_alarm),
                style = MaterialTheme.typography.labelLarge,
            )
        }

        item {
            Text(
                text = stringResource(R.string.next_alarm),
                style = MaterialTheme.typography.labelLarge,
            )
        }
        item {

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
        color = MaterialTheme.colorScheme.secondaryContainer
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



@Composable
fun Alarms(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AppTheme(dynamicColor = false, darkTheme = false) {
        HomeScreen()
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
