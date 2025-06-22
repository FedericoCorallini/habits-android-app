package com.fcorallini.habits.home.domain.home.usecases

import com.fcorallini.habits.home.domain.repository.HomeRepository
import javax.inject.Inject

class SyncHabitsUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke() {
        homeRepository.syncHabits()
    }
}