package com.example.alarmoffsetapp.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.alarmoffsetapp.data.Alarm
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

/**
 * Instrumented test for OfflineAlarmsRepository.
 */
@RunWith(AndroidJUnit4::class)
class OfflineAlarmsRepositoryTest {
    private lateinit var alarmDao: AlarmDao
    private lateinit var alarmDatabase: AlarmsDatabase
    private lateinit var repository: OfflineAlarmsRepository

    private var alarm1 = Alarm(
        id = 1,
        name = "alarm1",
        baseTime = LocalTime.of(6,24), // 123 minutes since midnight
        isRepeating = false,
        scheduledDate = null,
        daysOfWeek = setOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
        isVibrating = true,
        playsSound = true,
        isActive = true,
        canDismissOffsets = true,
        alarmGroup = null,
        alarmOffsets = listOf()
    )

    private var alarm2 = Alarm(
        id = 2,
        name = "alarm2",
        baseTime = LocalTime.of(13,59), // 123 minutes since midnight
        isRepeating = false,
        scheduledDate = LocalDate.now().plusDays(2),
        daysOfWeek = setOf(),
        isVibrating = true,
        playsSound = true,
        isActive = true,
        canDismissOffsets = true,
        alarmGroup = null,
        alarmOffsets = listOf()
    )

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        alarmDatabase = Room.inMemoryDatabaseBuilder(context, AlarmsDatabase::class.java)
            // Allowing main thread queries for testing.
            .allowMainThreadQueries()
            .build()
        alarmDao = alarmDatabase.alarmDao()
        repository = OfflineAlarmsRepository(alarmDao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        alarmDatabase.close()
    }

    @Test
    fun insertAlarm_emitsInStream() = runTest {
        repository.insertAlarm(alarm1)

        // Collect the first emission from the Flow
        val alarms = repository.getAllAlarmsStream().first()

        assertEquals(1, alarms.size)
        assertEquals(alarm1, alarms[0])
    }

    @Test
    fun insertMultipleAlarms_emitsInStream() = runTest {
        repository.insertAlarm(alarm1)
        repository.insertAlarm(alarm2)

        val alarms = repository.getAllAlarmsStream().first()

        assertEquals(2, alarms.size)
        assertEquals(alarm1, alarms[0])
        assertEquals(alarm2, alarms[1])
    }
}
