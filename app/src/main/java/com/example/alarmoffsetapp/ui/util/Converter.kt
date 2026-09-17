package com.example.alarmoffsetapp.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import com.example.alarmoffsetapp.R

import java.time.Duration

@Composable
fun durationToString(duration: Duration) : String {
    val days = duration.toDays().toInt()
    val hours = (duration.toHours() % 24).toInt()
    val minutes = (duration.toMinutes() % 60).toInt()
    if (duration.toDays() > 0) {
        return pluralStringResource(
                R.plurals.duration_days,
                days,
                days
            ) + if (hours > 0) " " + pluralStringResource(
                R.plurals.duration_hours,
                hours,
                hours
            ) else ""
    } else if (duration.toHours() > 0) {
        return pluralStringResource(
                    R.plurals.duration_hours, 
                    hours, 
                    hours
                ) + if (minutes > 0) " " + pluralStringResource(
            R.plurals.duration_minutes,
            minutes,
            minutes
        ) else ""
    } else if (duration.toMinutes() > 1){
        return pluralStringResource(
                R.plurals.duration_minutes,
                minutes,
                minutes
            )
    } else {
        return stringResource(R.string.less_than_a_minute)
    }
}
