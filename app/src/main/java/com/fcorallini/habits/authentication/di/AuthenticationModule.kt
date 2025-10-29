package com.fcorallini.habits.authentication.di

import android.provider.ContactsContract.CommonDataKinds.Email
import com.fcorallini.habits.authentication.data.matcher.EmailMatcherImpl
import com.fcorallini.habits.authentication.data.repository.AuthenticationRepositoryImpl
import com.fcorallini.habits.authentication.domain.matcher.EmailMatcher
import com.fcorallini.habits.authentication.domain.repository.AuthenticationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {
    @Provides
    @Singleton
    fun providesAuthenticationRepository(): AuthenticationRepository {
        return AuthenticationRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providesEmailMatcher(): EmailMatcher {
        return EmailMatcherImpl()
    }
}