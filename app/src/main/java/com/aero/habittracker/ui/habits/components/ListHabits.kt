package com.aero.habittracker.ui.habits.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.aero.habittracker.domain.Habit

@Composable
fun ListHabits(
    habits: List<Habit>,
    onToggleHabit: (Habit, Boolean) -> Unit,
    onDeleteHabit: (Habit) -> Unit
) {
    LazyColumn {
        items(habits, key = { it.id }) { habit ->
            HabitItem(
                habit = habit,
                onToggle = { checked ->
                    onToggleHabit(habit, checked)
                },
                onDelete = {
                    onDeleteHabit(habit)
                }
            )
        }
    }
}