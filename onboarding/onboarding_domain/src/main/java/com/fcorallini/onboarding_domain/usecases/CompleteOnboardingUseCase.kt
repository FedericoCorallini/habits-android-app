package com.fcorallini.onboarding_domain.usecases

import com.fcorallini.onboarding_domain.repository.OnboardingRepository

class CompleteOnboardingUseCase(
    private val repository: OnboardingRepository
) {
    operator fun invoke() {
        repository.completeOnboarding()
    }
}