package com.aero.habittracker.ui.habitDetail

import android.os.Build
import androidx.annotation.RequiresApi
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

@RequiresApi(Build.VERSION_CODES.O)
class HabitDetailViewModel(
    private val habitId: Int,
    private val repository: HabitRepository
) : ViewModel() {

    // El estado del hábito ahora es reactivo: observa la base de datos directamente
    val habit: StateFlow<Habit?> = repository.getHabitByIdFlow(habitId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

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

    fun updateHabit(habit: Habit) {
        viewModelScope.launch {
            try {
                repository.updateHabit(habit)
            } catch (e: Exception) {
                _error.value = "Error al actualizar el hábito: ${e.message}"
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun toggleCompletionStatus() {
        viewModelScope.launch {
            try {
                repository.toggleHabitCompletion(habitId)
            } catch (e: Exception) {
                _error.value = "Error al cambiar el estado: ${e.message}"
            }
        }
    }

    fun deleteHabit() {
        habit.value?.let { currentHabit ->
            viewModelScope.launch {
                try {
                    repository.deleteHabit(currentHabit)
                } catch (e: Exception) {
                    _error.value = "Error al eliminar el hábito: ${e.message}"
                }
            }
        }
    }

    fun addLogForDate(date: java.time.LocalDate) {
        viewModelScope.launch {
            try {
                repository.addHabitLogForDate(habitId, date)
            } catch (e: Exception) {
                _error.value = "Error al agregar registro: ${e.message}"
            }
        }
    }

    fun removeLogForDate(date: java.time.LocalDate) {
        viewModelScope.launch {
            try {
                repository.removeHabitLogForDate(habitId, date)
            } catch (e: Exception) {
                _error.value = "Error al eliminar registro: ${e.message}"
            }
        }
    }
}
