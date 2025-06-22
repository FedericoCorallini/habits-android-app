package com.fcorallini.habits.home.domain.detail.usecases

import com.fcorallini.habits.home.domain.model.Habit
import com.fcorallini.habits.home.domain.repository.HomeRepository
import java.time.ZonedDateTime
import javax.inject.Inject

class InsertHabitUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(habit: Habit) {
        homeRepository.insertHabit(habit)
    }
}