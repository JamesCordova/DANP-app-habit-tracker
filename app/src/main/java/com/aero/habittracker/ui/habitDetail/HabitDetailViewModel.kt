package com.aero.habittracker.ui.habitDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aero.habittracker.data.local.entity.HabitLogEntity
import com.aero.habittracker.data.repository.HabitRepository
import com.aero.habittracker.domain.Habit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HabitDetailViewModel(
    private val habitId: Int,
    private val repository: HabitRepository
) : ViewModel() {

    private val _habit = MutableStateFlow<Habit?>(null)
    val habit: StateFlow<Habit?> = _habit.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    val habitLogs: StateFlow<List<HabitLogEntity>> = repository.getHabitLogs(habitId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        loadHabit()
    }

    private fun loadHabit() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                val loadedHabit = repository.getHabitById(habitId)
                _habit.value = loadedHabit
                if (loadedHabit == null) {
                    _error.value = "Hábito no encontrado"
                }
            } catch (e: Exception) {
                _error.value = "Error al cargar el hábito: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateHabit(habit: Habit) {
        viewModelScope.launch {
            try {
                repository.updateHabit(habit)
                _habit.value = habit
            } catch (e: Exception) {
                _error.value = "Error al actualizar el hábito: ${e.message}"
            }
        }
    }

    fun toggleCompletionStatus() {
        viewModelScope.launch {
            try {
                repository.toggleHabitCompletion(habitId)
                // Recargar el hábito para obtener el estado actualizado
                val updatedHabit = repository.getHabitById(habitId)
                _habit.value = updatedHabit
            } catch (e: Exception) {
                _error.value = "Error al cambiar el estado: ${e.message}"
            }
        }
    }

    fun deleteHabit() {
        _habit.value?.let { currentHabit ->
            viewModelScope.launch {
                try {
                    repository.deleteHabit(currentHabit)
                } catch (e: Exception) {
                    _error.value = "Error al eliminar el hábito: ${e.message}"
                }
            }
        }
    }
}

