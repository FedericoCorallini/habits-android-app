package com.fcorallini.habits.onboarding.di

import android.content.Context
import android.content.SharedPreferences
import com.fcorallini.habits.onboarding.data.repository.OnboardingRepositoryImpl
import com.fcorallini.habits.onboarding.domain.repository.OnboardingRepository
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
}