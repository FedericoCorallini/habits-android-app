package com.fcorallini.onboarding_domain.repository

interface OnboardingRepository {
    fun hasSeenOnboarding() : Boolean
    fun completeOnboarding()
}