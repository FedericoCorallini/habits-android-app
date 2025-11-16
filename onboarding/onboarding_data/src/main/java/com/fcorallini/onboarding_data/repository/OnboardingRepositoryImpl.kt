package com.fcorallini.onboarding_data.repository

import android.content.SharedPreferences
import com.fcorallini.onboarding_domain.repository.OnboardingRepository
import androidx.core.content.edit

class OnboardingRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : OnboardingRepository {

    companion object {
        private const val HAS_SEEN_ONBOARDING = "has_seen_onboarding"
    }

    override fun hasSeenOnboarding(): Boolean {
        return sharedPreferences.getBoolean(HAS_SEEN_ONBOARDING, false)
    }

    override fun completeOnboarding() {
        sharedPreferences.edit() { putBoolean(HAS_SEEN_ONBOARDING, true) }
    }
}