package com.aero.habittracker.ui.habits

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.aero.habittracker.HabitTrackerApplication

fun habitTrackerViewModelFactory(application: HabitTrackerApplication) = viewModelFactory {
    initializer {
        HabitTrackerViewModel(application.repository)
    }
}

