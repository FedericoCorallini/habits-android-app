package com.fcorallini.home_domain.home.usecases

import com.fcorallini.home_domain.repository.HomeRepository

class SyncHabitsUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke() {
        homeRepository.syncHabits()
    }
}