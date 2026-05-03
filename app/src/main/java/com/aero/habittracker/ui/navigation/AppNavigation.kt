package com.aero.habittracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aero.habittracker.HabitTrackerApplication
import com.aero.habittracker.ui.habits.HabitTrackerScreen
import com.aero.habittracker.ui.welcome.WelcomeScreen

sealed class Screen(val route: String) {
    data object Welcome : Screen("welcome")
    data object HabitTracker : Screen("habit_tracker")
}

@Composable
fun AppNavigation(application: HabitTrackerApplication) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Welcome.route) {
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(
                onNavigateToHabits = {
                    navController.navigate(Screen.HabitTracker.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screen.HabitTracker.route) {
            HabitTrackerScreen(
                application = application,
                onNavigateBack = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.HabitTracker.route) { inclusive = true }
                    }
                }
            )
        }
    }
}


