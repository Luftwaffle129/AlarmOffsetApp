package com.example.alarmoffsetapp.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.alarmoffsetapp.data.database.data.AlarmEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.LocalDate

/**
 * Instrumented test for AlarmDao.
 */
@RunWith(AndroidJUnit4::class)
class AlarmDaoTest {
    private lateinit var alarmDao: AlarmDao
    private lateinit var alarmDatabase: AlarmsDatabase

    private var alarmEntity1 = AlarmEntity(
        id = 1,
        groupId = null,
        name = "alarm1",
        baseTime = 60 * 123, // 123 minutes since midnight
        isRepeating = false,
        scheduledDate = null,
        daysOfWeek = 1,
        isVibrating = true,
        playsSound = true,
        isActive = true,
        canDismissOffsets = true
    )

    private var alarmEntity2 = AlarmEntity(
        id = 2,
        groupId = null,
        name = "alarm2",
        baseTime = 60 * 123, // 123 minutes since midnight
        isRepeating = false,
        scheduledDate = LocalDate.now().plusDays(1).toEpochDay(), // tomorrow
        daysOfWeek = 0,
        isVibrating = true,
        playsSound = true,
        isActive = true,
        canDismissOffsets = true
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
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        alarmDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsAlarmIntoDB() = runBlocking {
        addOneAlarmToDb()
        val allItems = alarmDao.getAllAlarmsWithOffsets().first()
        Assert.assertEquals(allItems[0].alarm, alarmEntity1)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsMultipleItemsIntoDB() = runBlocking {
        addTwoAlarmsToDb()
        val allItems = alarmDao.getAllAlarmsWithOffsets().first()
        Assert.assertEquals(allItems[0].alarm, alarmEntity1)
        Assert.assertEquals(allItems[1].alarm, alarmEntity2)
    }

    private suspend fun addOneAlarmToDb() {
        alarmDao.insertAlarm(alarmEntity1)
    }

    private suspend fun addTwoAlarmsToDb() {
        alarmDao.insertAlarm(alarmEntity1)
        alarmDao.insertAlarm(alarmEntity2)
    }
}