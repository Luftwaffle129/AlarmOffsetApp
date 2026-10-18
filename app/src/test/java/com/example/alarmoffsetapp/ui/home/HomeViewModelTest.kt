package com.example.alarmoffsetapp.ui.home

import com.example.alarmoffsetapp.fake.FakeAlarmRepository
import com.example.alarmoffsetapp.rules.TestDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun debug_initialization() = runTest {
        println("Before repository")

        val repository = FakeAlarmRepository()

        println("Before ViewModel")
        val viewModel = HomeScreenViewModel(repository)
        println("After ViewModel")

        runCurrent()
        println("After runCurrent")
    }

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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun homeViewModel_getNextAlarm_isCorrect() = runTest {
        val repository = FakeAlarmRepository()
        val viewModel = HomeScreenViewModel(alarmsRepository = repository)

        runCurrent()

        val nextAlarm = viewModel.uiState.value.nextAlarm
        val alarm = viewModel.uiState.value.alarms[0]

        assertEquals(alarm.getNextAlarm(), nextAlarm)
    }
}
