package com.example.alarmoffsetapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.alarmoffsetapp.data.database.data.AlarmEntity
import com.example.alarmoffsetapp.data.database.data.AlarmGroupEntity
import com.example.alarmoffsetapp.data.database.data.AlarmOffsetEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

@Database(entities = [AlarmGroupEntity::class, AlarmEntity::class, AlarmOffsetEntity::class], version = 2, exportSchema = false)
abstract class AlarmsDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao

    companion object {
        @Volatile
        private var Instance: AlarmsDatabase? = null

        fun getDatabase(context: Context): AlarmsDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AlarmsDatabase::class.java, "alarm_database")
                    .fallbackToDestructiveMigration(true)
                    .addCallback(object : Callback() {
                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)

                            CoroutineScope(Dispatchers.IO).launch {
                                populateInitialData(Instance?.alarmDao())
                            }
                        }
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            CoroutineScope(Dispatchers.IO).launch {
                                populateInitialData(Instance?.alarmDao())
                            }
                        }
                    })
                    .build()
                    .also { Instance = it }
            }
        }

        private suspend fun populateInitialData(alarmDao: AlarmDao?) {
            val initialAlarm = AlarmEntity(
                id = 0, // Room autoGenerate will assign ID
                groupId = null,
                name = "Default Alarm",
                baseTime = 8 * 60 * 60,
                isRepeating = false,
                scheduledDate = LocalDate.now().plusDays(1).toEpochDay(),
                daysOfWeek = 0,
                isVibrating = true,
                playsSound = true,
                isActive = true,
                canDismissOffsets = true
            )
            alarmDao?.insertAlarm(initialAlarm)
        }
    }
}