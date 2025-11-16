package com.fcorallini.home_domain.repository

import com.fcorallini.home_domain.model.Habit
import kotlinx.coroutines.flow.Flow
import java.time.ZonedDateTime

interface HomeRepository {
    fun getAllHabitsForSelectedDate(date : ZonedDateTime) : Flow<List<Habit>>
    suspend fun insertHabit(habit: Habit)
    suspend fun getHabitById(id: String): Habit
    suspend fun syncHabits()
}