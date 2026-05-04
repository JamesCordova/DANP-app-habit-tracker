package com.aero.habittracker.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aero.habittracker.data.local.entity.HabitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("""
        SELECT h.id, h.title, CASE 
            WHEN hl.id IS NOT NULL THEN 1 
            ELSE 0 
        END as isCompletedToday
        FROM habits h
        LEFT JOIN habit_logs hl ON h.id = hl.habitId 
            AND hl.date = :date
        ORDER BY h.id DESC
    """)
    fun getAllHabits(date: java.time.LocalDate): Flow<List<HabitWithCompletion>>

    @Query("""
        SELECT h.id, h.title, CASE 
            WHEN hl.id IS NOT NULL THEN 1 
            ELSE 0 
        END as isCompletedToday
        FROM habits h
        LEFT JOIN habit_logs hl ON h.id = hl.habitId 
            AND hl.date = :date
        WHERE hl.id IS NOT NULL
        ORDER BY h.id DESC
    """)
    fun getCompletedHabits(date: java.time.LocalDate): Flow<List<HabitWithCompletion>>

    @Query("""
        SELECT h.id, h.title, CASE 
            WHEN hl.id IS NOT NULL THEN 1 
            ELSE 0 
        END as isCompletedToday
        FROM habits h
        LEFT JOIN habit_logs hl ON h.id = hl.habitId 
            AND hl.date = :date
        WHERE hl.id IS NULL
        ORDER BY h.id DESC
    """)
    fun getPendingHabits(date: java.time.LocalDate): Flow<List<HabitWithCompletion>>

    @Query("""
        SELECT h.id, h.title, CASE 
            WHEN hl.id IS NOT NULL THEN 1 
            ELSE 0 
        END as isCompletedToday
        FROM habits h
        LEFT JOIN habit_logs hl ON h.id = hl.habitId 
            AND hl.date = :date
        WHERE h.id = :id
    """)
    fun getHabitByIdFlow(id: Int, date: java.time.LocalDate): Flow<HabitWithCompletion?>

    @Query("""
        SELECT h.id, h.title, CASE 
            WHEN hl.id IS NOT NULL THEN 1 
            ELSE 0 
        END as isCompletedToday
        FROM habits h
        LEFT JOIN habit_logs hl ON h.id = hl.habitId 
            AND hl.date = :date
        WHERE h.id = :id
    """)
    suspend fun getHabitById(id: Int, date: java.time.LocalDate): HabitWithCompletion?

    @Insert
    suspend fun insertHabit(habit: HabitEntity): Long

    @Update
    suspend fun updateHabit(habit: HabitEntity)

    @Delete
    suspend fun deleteHabit(habit: HabitEntity)

    @Query("DELETE FROM habits")
    suspend fun deleteAllHabits()
}

// DTO para retornar Habit con isCompletedToday calculado
data class HabitWithCompletion(
    val id: Int,
    val title: String,
    val isCompletedToday: Boolean
)

