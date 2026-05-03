package com.aero.habittracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class HabitEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String
)