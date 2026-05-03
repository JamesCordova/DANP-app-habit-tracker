package com.aero.habittracker.ui.habits

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aero.habittracker.data.repository.HabitRepository
import com.aero.habittracker.domain.Habit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class HabitFilter {
    ALL, COMPLETED, PENDING
}

class HabitTrackerViewModel(private val repository: HabitRepository) : ViewModel() {
    var input by mutableStateOf("")
        private set

    private val filterStateFlow = MutableStateFlow(HabitFilter.ALL)

    val currentFilterFlow: StateFlow<HabitFilter> = filterStateFlow

    val habits: StateFlow<List<Habit>> = repository.getAllHabits()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val filteredHabits: StateFlow<List<Habit>> = filterStateFlow.flatMapLatest { filter ->
        when (filter) {
            HabitFilter.ALL -> repository.getAllHabits()
            HabitFilter.COMPLETED -> repository.getCompletedHabits()
            HabitFilter.PENDING -> repository.getPendingHabits()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val completedCount: Int
        get() = habits.value.count { it.isCompletedToday }

    val progress: Float
        get() = if (habits.value.isNotEmpty()) {
            completedCount.toFloat() / habits.value.size
        } else 0.0f

    fun onInputChange(newInput: String) {
        input = newInput
    }

    fun setFilter(filter: HabitFilter) {
        filterStateFlow.value = filter
    }

    fun addHabit() {
        if (input.isNotBlank()) {
            val newHabit = Habit(
                id = 0,
                title = input,
                isCompletedToday = false
            )
            viewModelScope.launch {
                repository.insertHabit(newHabit)
                input = ""
            }
        }
    }

    fun toggleHabit(habit: Habit, isChecked: Boolean) {
        viewModelScope.launch {
            repository.toggleHabitCompletion(habit.id)
        }
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            repository.deleteHabit(habit)
        }
    }
}