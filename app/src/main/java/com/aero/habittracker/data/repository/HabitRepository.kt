package com.aero.habittracker.data.repository

import com.aero.habittracker.data.local.dao.HabitDao
import com.aero.habittracker.data.local.entity.HabitEntity
import com.aero.habittracker.domain.Habit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HabitRepository(private val habitDao: HabitDao) {

    fun getAllHabits(): Flow<List<Habit>> {
        return habitDao.getAllHabits().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    fun getCompletedHabits(): Flow<List<Habit>> {
        return habitDao.getCompletedHabits().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    fun getPendingHabits(): Flow<List<Habit>> {
        return habitDao.getPendingHabits().map { entities ->
            entities.map { it.toDomain() }
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
}

fun HabitEntity.toDomain() = Habit(
    id = id,
    title = title,
    isCompletedToday = isCompletedToday
)

fun Habit.toEntity() = HabitEntity(
    id = id,
    title = title,
    isCompletedToday = isCompletedToday
)
