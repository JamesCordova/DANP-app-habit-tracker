package com.aero.habittracker.domain

data class Habit (
    val id: Int,
    val title: String,
    val isCompletedToday: Boolean
)