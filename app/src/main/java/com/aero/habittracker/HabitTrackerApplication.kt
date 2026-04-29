package com.aero.habittracker

import android.app.Application
import com.aero.habittracker.data.local.db.AppDatabase
import com.aero.habittracker.data.repository.HabitRepository

class HabitTrackerApplication : Application() {
    private val database by lazy { AppDatabase.getInstance(this) }
    val repository by lazy { HabitRepository(database.habitDao()) }
}

