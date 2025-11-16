package com.fcorallini.onboarding_domain.usecases

import com.fcorallini.onboarding_domain.repository.OnboardingRepository

class HasSeenOnboardingUseCase(
    private val repository: OnboardingRepository
) {
    operator fun invoke() : Boolean {
        return repository.hasSeenOnboarding()
    }
}