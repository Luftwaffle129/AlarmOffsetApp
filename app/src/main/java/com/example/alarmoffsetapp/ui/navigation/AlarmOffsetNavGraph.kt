package com.example.alarmoffsetapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alarmoffsetapp.ui.editAlarm.EditAlarmDestination
import com.example.alarmoffsetapp.ui.editAlarm.EditAlarmScreen
import com.example.alarmoffsetapp.ui.home.HomeDestination
import com.example.alarmoffsetapp.ui.home.HomeScreen

//enum class AlarmOffsetScreen(@StringRes val title: Int) {
//    HomeScreen(title = R.string.home_screen),
//    EditAlarm(title = R.string.edit_alarm),
//    AddAlarm(title = R.string.add_alarm),
//}

@Composable
fun AlarmOffsetNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                is24HourFormat = true,
                navigateToAlarmAdd = { EditAlarmDestination.route },
                navigateToAlarmEdit = { EditAlarmDestination.routeWithArgs },
                navigateToGroupAlarmEdit = {  },
                navigateToViewGroupAlarms = {  },
                navigateToSettings = {  },
                modifier = Modifier
            )
        }
//        composable(route = EditAlarmDestination.route) {
//            EditAlarmScreen(
//                is24HourFormat = true,
//                modifier = Modifier
//            )
//        }
//        composable(route = EditAlarmDestination.routeWithArgs) {
//
//        }
    }
}