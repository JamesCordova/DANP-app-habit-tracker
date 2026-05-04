package com.aero.habittracker.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.aero.habittracker.data.local.entity.HabitLogEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface HabitLogDao {

    @Insert
    suspend fun insertLog(log: HabitLogEntity): Long

    @Delete
    suspend fun deleteLog(log: HabitLogEntity)

    @Query("SELECT * FROM habit_logs WHERE habitId = :habitId ORDER BY date DESC")
    fun getLogsByHabit(habitId: Int): Flow<List<HabitLogEntity>>

    @Query("SELECT * FROM habit_logs WHERE habitId = :habitId AND date = :date")
    suspend fun getLogByHabitAndDate(habitId: Int, date: LocalDate): HabitLogEntity?

    @Query("SELECT * FROM habit_logs WHERE habitId = :habitId AND date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getLogsByHabitInDateRange(habitId: Int, startDate: LocalDate, endDate: LocalDate): Flow<List<HabitLogEntity>>

    @Query("DELETE FROM habit_logs WHERE habitId = :habitId AND date = :date")
    suspend fun deleteLogByHabitAndDate(habitId: Int, date: LocalDate)

    @Query("SELECT * FROM habit_logs ORDER BY date DESC")
    fun getAllLogs(): Flow<List<HabitLogEntity>>
}

