package com.fcorallini.habits.home.data.repository

import com.fcorallini.home_domain.model.Habit
import com.fcorallini.home_domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import java.time.ZonedDateTime

class FakeHomeRepository  : com.fcorallini.home_domain.repository.HomeRepository {
    private var habits = emptyList<com.fcorallini.home_domain.model.Habit>()
    private val habitsFlow = MutableSharedFlow<List<com.fcorallini.home_domain.model.Habit>>()

    override fun getAllHabitsForSelectedDate(date: ZonedDateTime) = habitsFlow

    override suspend fun insertHabit(habit: com.fcorallini.home_domain.model.Habit) {
        habits = habits + habit
        habitsFlow.emit(habits)
    }

    override suspend fun getHabitById(id: String) = habits.first {
        id == it.id
    }

    override suspend fun syncHabits() {}
}