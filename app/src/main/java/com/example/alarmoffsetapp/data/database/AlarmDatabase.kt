package com.example.alarmoffsetapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AlarmGroupEntity::class, AlarmEntity::class, AlarmOffsetEntity::class], version = 1, exportSchema = false)
abstract class AlarmsDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao

    companion object {
        @Volatile
        private var Instance: AlarmsDatabase? = null

        fun getDatabase(context: Context): AlarmsDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AlarmsDatabase::class.java, "alarm_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}