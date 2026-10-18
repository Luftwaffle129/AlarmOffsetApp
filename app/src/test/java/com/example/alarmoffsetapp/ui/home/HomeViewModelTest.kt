package com.example.alarmoffsetapp.ui.home

import com.example.alarmoffsetapp.fake.FakeAlarmRepository
import com.example.alarmoffsetapp.fake.FakeDataSource
import com.example.alarmoffsetapp.rules.TestDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    /**
     * Used for debugging the viewmodel for testing. Necessary for debugging flows
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Ignore("Used for fixing viewmodel")
    fun debug_initialization() = runTest {
        println("Before repository")

        val repository = FakeAlarmRepository()

        println("Before ViewModel")
        val viewModel = HomeScreenViewModel(repository)
        println("After ViewModel")

        runCurrent()
        println("After runCurrent")
    }

    /**
     * Check onAlarmToggle() toggles an alarms active state and that it is updated in the database and viewmodel
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun homeViewModel_onAlarmToggle_isOppositeActiveState() = runTest {
        val repository = FakeAlarmRepository()
        val viewModel = HomeScreenViewModel(alarmsRepository = repository)

        runCurrent()

        val alarm = viewModel.uiState.value.alarms[0]
        val isActive = alarm.isActive // initial state

        // toggle alarm
        viewModel.onAlarmToggle(alarm)
        advanceUntilIdle()
        assertNotEquals(isActive, viewModel.uiState.value.alarms[0].isActive)

        // toggle alarm
        val updatedAlarm = viewModel.uiState.value.alarms[0]
        viewModel.onAlarmToggle(updatedAlarm)
        advanceUntilIdle()
        assertEquals(isActive, viewModel.uiState.value.alarms[0].isActive)
    }
    /**
     * Check getNextAlarm() returns the correct alarm when two active alarms are present with differing times.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun homeViewModel_getNextAlarm_isCorrectWithAlarms() = runTest {
        val repository = FakeAlarmRepository()
        val viewModel = HomeScreenViewModel(alarmsRepository = repository)

        runCurrent()

        val nextAlarm = viewModel.uiState.value.nextAlarm
        val alarm = viewModel.uiState.value.alarms[0]

        assertEquals(alarm.getNextAlarm(), nextAlarm)
    }
    /**
     * Check getNextAlarm() returns the correct time when there is only one active alarm
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun homeViewModel_getNextAlarm_isCorrectWithActiveAlarm() = runTest {
        val repository = FakeAlarmRepository.empty().apply {
            insertAlarm( FakeDataSource().alarmList[0].copy(isActive = true) )
        }
        val viewModel = HomeScreenViewModel(alarmsRepository = repository)

        runCurrent()

        val nextAlarm = viewModel.uiState.value.nextAlarm
        val alarm = viewModel.uiState.value.alarms[0]

        assertEquals(alarm.getNextAlarm(), nextAlarm)
    }
    /**
     * Check getNextAlarm() returns null when there are no active alarms
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun homeViewModel_getNextAlarm_isNullWithInactiveAlarm() = runTest {
        val repository = FakeAlarmRepository.empty().apply {
            insertAlarm( FakeDataSource().alarmList[0].copy(isActive = false) )
        }
        val viewModel = HomeScreenViewModel(alarmsRepository = repository)

        runCurrent()

        val nextAlarm = viewModel.uiState.value.nextAlarm

        assertEquals(null, nextAlarm)
    }
    /**
     * Check getNextAlarm() returns null when there are no alarms
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun homeViewModel_getNextAlarm_isNullWithNoAlarms() = runTest {
        val repository = FakeAlarmRepository.empty()
        val viewModel = HomeScreenViewModel(alarmsRepository = repository)

        runCurrent()

        val nextAlarm = viewModel.uiState.value.nextAlarm

        assertEquals(null, nextAlarm)
    }
}
