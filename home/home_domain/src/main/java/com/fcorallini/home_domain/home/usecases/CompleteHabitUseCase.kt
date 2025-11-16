package com.fcorallini.home_domain.home.usecases

import com.fcorallini.home_domain.model.Habit
import com.fcorallini.home_domain.repository.HomeRepository
import java.time.ZonedDateTime

class CompleteHabitUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(habit: Habit, date : ZonedDateTime) {
        val newHabit = if (habit.completedDates.contains(date.toLocalDate())) {
            habit.copy(
                completedDates = habit.completedDates - date.toLocalDate()
            )
        } else {
            habit.copy(
                completedDates = habit.completedDates + date.toLocalDate()
            )
        }
        homeRepository.insertHabit(newHabit)
    }
}