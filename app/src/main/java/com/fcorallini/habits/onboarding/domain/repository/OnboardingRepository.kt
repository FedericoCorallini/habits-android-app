package com.fcorallini.habits.onboarding.domain.repository

interface OnboardingRepository {
    fun hasSeenOnboarding() : Boolean
    fun completeOnboarding()
}