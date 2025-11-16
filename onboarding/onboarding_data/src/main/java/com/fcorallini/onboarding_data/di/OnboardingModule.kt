package com.fcorallini.onboarding_data.di

import android.content.Context
import android.content.SharedPreferences
import com.fcorallini.onboarding_data.repository.OnboardingRepositoryImpl
import com.fcorallini.onboarding_domain.repository.OnboardingRepository
import com.fcorallini.onboarding_domain.usecases.CompleteOnboardingUseCase
import com.fcorallini.onboarding_domain.usecases.HasSeenOnboardingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object OnboardingModule {

    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context) : SharedPreferences {
        return context.getSharedPreferences("habits_onboarding_preference", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideOnboardingRepository(sharedPreferences: SharedPreferences) : OnboardingRepository {
        return OnboardingRepositoryImpl(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideCompleteOnboardingUseCase(onboardingRepository: OnboardingRepository) : CompleteOnboardingUseCase {
        return CompleteOnboardingUseCase(onboardingRepository)
    }

    @Provides
    @Singleton
    fun provideHasSeenOnboardingUseCase(onboardingRepository: OnboardingRepository) : HasSeenOnboardingUseCase {
        return HasSeenOnboardingUseCase(onboardingRepository)
    }
}