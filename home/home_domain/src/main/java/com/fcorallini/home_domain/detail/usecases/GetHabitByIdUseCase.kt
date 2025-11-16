package com.fcorallini.home_domain.detail.usecases

import com.fcorallini.home_domain.model.Habit
import com.fcorallini.home_domain.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetHabitByIdUseCase(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(id : String) : Habit {
        return withContext(Dispatchers.IO) { repository.getHabitById(id) }
    }
}