package com.fcorallini.habits.onboarding.domain.usecases

import com.fcorallini.habits.onboarding.domain.repository.OnboardingRepository
import javax.inject.Inject

class HasSeenOnboardingUseCase @Inject constructor(
    private val repository: OnboardingRepository
) {
    operator fun invoke() : Boolean {
        return repository.hasSeenOnboarding()
    }
}