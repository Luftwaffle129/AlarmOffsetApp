package com.example.alarmoffsetapp.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.toLowerCase
import com.example.alarmoffsetapp.R

import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

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
    } else if (duration.toMinutes() >= 1){
        return pluralStringResource(
                R.plurals.duration_minutes,
                minutes,
                minutes
            )
    } else {
        return stringResource(R.string.less_than_a_minute)
    }
}

@Composable
fun dateTimeToDateString(dateTime: LocalDateTime) : String {
    val pattern = "d MMM"
    return dateTime.format(DateTimeFormatter.ofPattern(pattern)) // return date format with capitalized first letters
        .lowercase()
        .split(" ")
        .joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } } // English only, apply locale to handle other languages
}


@Composable
fun dateTimeToTimeString(dateTime: LocalDateTime, is24HourFormat: Boolean = true) : String {
    val pattern = if (is24HourFormat) "H:mm" else "h:mm"
    return dateTime.format(DateTimeFormatter.ofPattern(pattern))
}

@Composable
fun getAmOrPm(dateTime: LocalDateTime) : String {
    val pattern = "a"
    return dateTime.format(DateTimeFormatter.ofPattern(pattern))
}
