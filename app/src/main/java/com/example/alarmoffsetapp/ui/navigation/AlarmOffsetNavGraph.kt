package com.example.alarmoffsetapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alarmoffsetapp.ui.editAlarm.EditAlarmScreen
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
                navigateToAlarmAdd = { navController.navigate(AlarmOffsetScreen.AddAlarm.name) },
                navigateToAlarmEdit = { navController.navigate(AlarmOffsetScreen.EditAlarm.name) },
                navigateToGroupAlarm = {},
                modifier = Modifier
            )
        }
        composable(route = AlarmOffsetScreen.AddAlarm.name) {
            EditAlarmScreen(
                is24HourFormat = true,
                modifier = Modifier
            )
        }
        composable(route = AlarmOffsetScreen.EditAlarm.name) {

        }
    }
}