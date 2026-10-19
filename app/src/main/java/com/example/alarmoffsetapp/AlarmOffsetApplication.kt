package com.example.alarmoffsetapp

import android.app.Application
import com.example.alarmoffsetapp.data.AppContainer
import com.example.alarmoffsetapp.data.AppDataContainer

/**
 * An application container that provides dependencies.
 */
class AlarmOffsetApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}