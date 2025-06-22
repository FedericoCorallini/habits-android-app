package com.fcorallini.habits.home.domain.home.usecases

import com.fcorallini.habits.home.domain.model.Habit
import com.fcorallini.habits.home.domain.repository.HomeRepository
import java.time.ZonedDateTime
import javax.inject.Inject

class CompleteHabitUseCase @Inject constructor(
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