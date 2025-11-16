package com.fcorallini.home_domain.detail.usecases

import com.fcorallini.home_domain.model.Habit
import com.fcorallini.home_domain.repository.HomeRepository

class InsertHabitUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(habit: Habit) {
        homeRepository.insertHabit(habit)
    }
}