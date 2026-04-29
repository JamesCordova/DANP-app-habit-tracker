package com.aero.habittracker.ui.habits

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.aero.habittracker.domain.Habit


class HabitTrackerViewModel : ViewModel() {
    var input by mutableStateOf("")
        private set

    var habits = mutableStateListOf<Habit>()
        private set

    val completedCount: Int
        get() = habits.count { it.isCompletedToday }

    val progress: Float
        get() = if (habits.isNotEmpty()) {
            completedCount.toFloat() / habits.size
        } else 0.0f

    fun onInputChange(newInput: String) {
        input = newInput
    }

    fun addHabit() {
        if (input.isNotBlank()) {
            val newHabit = Habit(
                id = habits.size + 1,
                title = input,
                isCompletedToday = false
            )
            habits.add(newHabit)
            input = ""
        }
    }

    fun toggleHabit(habit: Habit, isChecked: Boolean) {
        val index = habits.indexOfFirst { it.id == habit.id }
        if (index != -1) {
            habits[index] = habit.copy(isCompletedToday = isChecked)
        }
    }

    fun deleteHabit(habit: Habit) {
        habits.removeIf { it.id == habit.id }
    }
}