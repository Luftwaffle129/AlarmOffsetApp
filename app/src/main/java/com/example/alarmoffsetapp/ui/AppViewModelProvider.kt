package com.example.alarmoffsetapp.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.alarmoffsetapp.AlarmOffsetApplication
import com.example.alarmoffsetapp.ui.editAlarm.EditAlarmViewModel
import com.example.alarmoffsetapp.ui.home.HomeScreenViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeScreenViewModel(
                alarmOffsetApplication().container.alarmsRepository
            )
        }
        initializer {
            EditAlarmViewModel(
                alarmOffsetApplication().container.alarmsRepository
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [AlarmOffsetApplication].
 */
fun CreationExtras.alarmOffsetApplication(): AlarmOffsetApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as AlarmOffsetApplication)
