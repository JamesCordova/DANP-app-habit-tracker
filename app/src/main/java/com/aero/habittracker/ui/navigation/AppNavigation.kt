package com.aero.habittracker.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aero.habittracker.HabitTrackerApplication
import com.aero.habittracker.ui.habitDetail.HabitDetailScreen
import com.aero.habittracker.ui.habits.HabitTrackerScreen
import com.aero.habittracker.ui.welcome.WelcomeScreen

sealed class Screen(val route: String) {
    data object Welcome : Screen("welcome")
    data object HabitTracker : Screen("habit_tracker")
    data class HabitDetail(val habitId: Int) : Screen("habit_detail/{habitId}") {
        fun createRoute(habitId: Int) = "habit_detail/$habitId"
    }
}

@RequiresApi(Build.VERSION_CODES.O)
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
                },
                onNavigateToDetail = { habitId ->
                    navController.navigate(Screen.HabitDetail(habitId).createRoute(habitId))
                }
            )
        }

        composable(
            route = Screen.HabitDetail(0).route,
            arguments = listOf(
                navArgument("habitId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val habitId = backStackEntry.arguments?.getInt("habitId") ?: 0
            HabitDetailScreen(
                habitId = habitId,
                application = application,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}


