package com.fcorallini.habits.home.domain.detail.usecases

import com.fcorallini.habits.home.domain.model.Habit
import com.fcorallini.habits.home.domain.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetHabitByIdUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(id : String) : Habit {
        return withContext(Dispatchers.IO) { repository.getHabitById(id) }
    }
}