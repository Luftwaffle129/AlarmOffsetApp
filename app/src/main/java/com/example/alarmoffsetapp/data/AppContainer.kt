package com.example.alarmoffsetapp.data

import android.content.Context
import com.example.alarmoffsetapp.data.database.AlarmsDatabase
import com.example.alarmoffsetapp.data.database.AlarmsRepository
import com.example.alarmoffsetapp.data.database.OfflineAlarmsRepository

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val alarmsRepository: AlarmsRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */
    override val alarmsRepository: AlarmsRepository by lazy {
        OfflineAlarmsRepository(AlarmsDatabase.getDatabase(context).alarmDao())
    }
}