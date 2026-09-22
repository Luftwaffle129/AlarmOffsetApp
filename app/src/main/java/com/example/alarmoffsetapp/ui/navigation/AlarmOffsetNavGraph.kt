package com.example.alarmoffsetapp.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alarmoffsetapp.R
import com.example.alarmoffsetapp.ui.home.HomeScreen

//enum class AlarmOffsetScreen(@StringRes val title: Int) {
//    HomeScreen(title = R.string.home_screen),
//    EditAlarm(title = R.string.edit_alarm),
//    AddAlarm(title = R.string.add_alarm),
//}

enum class AlarmOffsetScreen() {
    HomeScreen,
    EditAlarm,
    AddAlarm,
}

@Composable
fun AlarmOffsetNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = AlarmOffsetScreen.HomeScreen.name,
        modifier = modifier
    ) {
        composable(route = AlarmOffsetScreen.HomeScreen.name) {
            HomeScreen(
                is24HourFormat = true,
                modifier = Modifier
            )
        }
    }
}