import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4

import com.example.alarmoffsetapp.data.database.AlarmDao
import com.example.alarmoffsetapp.data.database.AlarmEntity
import com.example.alarmoffsetapp.data.database.AlarmsDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import java.io.IOException
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class AlarmDaoTest {
    private lateinit var alarmDao: AlarmDao
    private lateinit var alarmDatabase: AlarmsDatabase

    private var alarm1 = AlarmEntity(
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

    private var alarm2 = AlarmEntity(
        id = 2,
        groupId = null,
        name = "alarm2",
        baseTime = 60 * 123, // 123 minutes since midnight
        isRepeating = false,
        scheduledDate = LocalDate.now().plusDays(1).toString(), // tomorrow
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
        val allItems = alarmDao.getAllAlarms().first()
        assertEquals(allItems[0].alarm, alarm1)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsMultipleItemsIntoDB() = runBlocking {
        addTwoAlarmsToDb()
        val allItems = alarmDao.getAllAlarms().first()
        assertEquals(allItems[0].alarm, alarm1)
        assertEquals(allItems[1].alarm, alarm2)
    }

    private suspend fun addOneAlarmToDb() {
        alarmDao.insertAlarm(alarm1)
    }

    private suspend fun addTwoAlarmsToDb() {
        alarmDao.insertAlarm(alarm1)
        alarmDao.insertAlarm(alarm2)
    }
}
