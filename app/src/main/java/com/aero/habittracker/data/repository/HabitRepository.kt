package com.aero.habittracker.data.repository

import com.aero.habittracker.data.local.dao.HabitDao
import com.aero.habittracker.data.local.dao.HabitLogDao
import com.aero.habittracker.data.local.entity.HabitEntity
import com.aero.habittracker.data.local.entity.HabitLogEntity
import com.aero.habittracker.domain.Habit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class HabitRepository(
    private val habitDao: HabitDao,
    private val habitLogDao: HabitLogDao
) {

    fun getAllHabits(): Flow<List<Habit>> {
        return habitDao.getAllHabits().map { habits ->
            habits.map { it.toDomain() }
        }
    }

    fun getCompletedHabits(): Flow<List<Habit>> {
        return habitDao.getCompletedHabits().map { habits ->
            habits.map { it.toDomain() }
        }
    }

    fun getPendingHabits(): Flow<List<Habit>> {
        return habitDao.getPendingHabits().map { habits ->
            habits.map { it.toDomain() }
        }
    }

    suspend fun insertHabit(habit: Habit): Long {
        return habitDao.insertHabit(habit.toEntity())
    }

    suspend fun updateHabit(habit: Habit) {
        habitDao.updateHabit(habit.toEntity())
    }

    suspend fun deleteHabit(habit: Habit) {
        habitDao.deleteHabit(habit.toEntity())
    }

    suspend fun getHabitById(id: Int): Habit? {
        return habitDao.getHabitById(id)?.toDomain()
    }

    // Métodos para manejar logs
    suspend fun toggleHabitCompletion(habitId: Int) {
        val today = LocalDate.now()
        val existingLog = habitLogDao.getLogByHabitAndDate(habitId, today)

        if (existingLog != null) {
            // Si existe log de hoy, lo eliminamos (lo desmarcamos)
            habitLogDao.deleteLog(existingLog)
        } else {
            // Si no existe, lo insertamos (lo marcamos como completo hoy)
            habitLogDao.insertLog(HabitLogEntity(habitId = habitId, date = today))
        }
    }

    fun getHabitLogs(habitId: Int): Flow<List<HabitLogEntity>> {
        return habitLogDao.getLogsByHabit(habitId)
    }

    fun getHabitLogsInRange(habitId: Int, startDate: LocalDate, endDate: LocalDate): Flow<List<HabitLogEntity>> {
        return habitLogDao.getLogsByHabitInDateRange(habitId, startDate, endDate)
    }

    suspend fun getHabitLogByDate(habitId: Int, date: LocalDate): HabitLogEntity? {
        return habitLogDao.getLogByHabitAndDate(habitId, date)
    }
}

// Extension para mapeo
fun com.aero.habittracker.data.local.dao.HabitWithCompletion.toDomain() = Habit(
    id = id,
    title = title,
    isCompletedToday = isCompletedToday
)

fun Habit.toEntity() = HabitEntity(
    id = id,
    title = title
)
