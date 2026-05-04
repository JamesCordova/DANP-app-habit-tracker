package com.aero.habittracker.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.aero.habittracker.data.local.dao.HabitDao
import com.aero.habittracker.data.local.dao.HabitLogDao
import com.aero.habittracker.data.local.dao.HabitWithCompletion
import com.aero.habittracker.data.local.entity.HabitEntity
import com.aero.habittracker.data.local.entity.HabitLogEntity
import com.aero.habittracker.domain.Habit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class HabitRepository(
    private val habitDao: HabitDao,
    private val habitLogDao: HabitLogDao
) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAllHabits(date: LocalDate = LocalDate.now()): Flow<List<Habit>> {
        return combine(
            habitDao.getAllHabits(date),
            habitLogDao.getAllLogs()
        ) { habits, allLogs ->
            val logsByHabit = allLogs.groupBy { it.habitId }
            habits.map { it.toDomain(logsByHabit[it.id] ?: emptyList()) }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCompletedHabits(date: LocalDate = LocalDate.now()): Flow<List<Habit>> {
        return combine(
            habitDao.getCompletedHabits(date),
            habitLogDao.getAllLogs()
        ) { habits, allLogs ->
            val logsByHabit = allLogs.groupBy { it.habitId }
            habits.map { it.toDomain(logsByHabit[it.id] ?: emptyList()) }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getPendingHabits(date: LocalDate = LocalDate.now()): Flow<List<Habit>> {
        return combine(
            habitDao.getPendingHabits(date),
            habitLogDao.getAllLogs()
        ) { habits, allLogs ->
            val logsByHabit = allLogs.groupBy { it.habitId }
            habits.map { it.toDomain(logsByHabit[it.id] ?: emptyList()) }
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

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getHabitById(id: Int, date: LocalDate = LocalDate.now()): Habit? {
        val habitWithCompletion = habitDao.getHabitById(id, date) ?: return null
        val logs = habitLogDao.getLogsByHabit(id).first()
        return habitWithCompletion.toDomain(logs)
    }

    // Métodos para manejar logs
    @RequiresApi(Build.VERSION_CODES.O)
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

    suspend fun addHabitLogForDate(habitId: Int, date: LocalDate) {
        val existingLog = habitLogDao.getLogByHabitAndDate(habitId, date)
        if (existingLog == null) {
            habitLogDao.insertLog(HabitLogEntity(habitId = habitId, date = date))
        }
    }

    suspend fun removeHabitLogForDate(habitId: Int, date: LocalDate) {
        habitLogDao.deleteLogByHabitAndDate(habitId, date)
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
@RequiresApi(Build.VERSION_CODES.O)
fun HabitWithCompletion.toDomain(logs: List<HabitLogEntity>) = Habit(
    id = id,
    title = title,
    isCompletedToday = isCompletedToday,
    streak = calculateStreak(logs)
)

@RequiresApi(Build.VERSION_CODES.O)
private fun calculateStreak(logs: List<HabitLogEntity>): Int {
    if (logs.isEmpty()) return 0
    
    val sortedDates = logs.map { it.date }.distinct().sortedDescending()
    val today = LocalDate.now()
    val yesterday = today.minusDays(1)
    
    var streak = 0
    var nextExpectedDate = if (sortedDates[0] == today) {
        today
    } else if (sortedDates[0] == yesterday) {
        yesterday
    } else {
        return 0 // Racha rota si no hay nada hoy ni ayer
    }
    
    for (date in sortedDates) {
        if (date == nextExpectedDate) {
            streak++
            nextExpectedDate = nextExpectedDate.minusDays(1)
        } else {
            break
        }
    }
    
    return streak
}

fun Habit.toEntity() = HabitEntity(
    id = id,
    title = title
)
