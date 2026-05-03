package com.aero.habittracker.ui.habitDetail

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.aero.habittracker.HabitTrackerApplication

fun habitDetailViewModelFactory(
    application: HabitTrackerApplication,
    habitId: Int
) = viewModelFactory {
    initializer {
        HabitDetailViewModel(habitId, application.repository)
    }
}
