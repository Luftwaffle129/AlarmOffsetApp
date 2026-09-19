package com.example.alarmoffsetapp

import android.app.Application
import com.example.alarmoffsetapp.data.AppContainer
import com.example.alarmoffsetapp.data.AppDataContainer

class AlarmApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}