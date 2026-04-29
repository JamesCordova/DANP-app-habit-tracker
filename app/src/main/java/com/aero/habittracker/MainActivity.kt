package com.aero.habittracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import com.aero.habittracker.ui.habits.HabitTrackerScreen
import com.aero.habittracker.ui.theme.HabitTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HabitTrackerTheme {
                val application = LocalContext.current.applicationContext as HabitTrackerApplication
                HabitTrackerScreen(application)
            }
        }
    }
}