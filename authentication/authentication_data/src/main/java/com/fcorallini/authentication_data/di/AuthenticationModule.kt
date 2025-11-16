package com.fcorallini.authentication_data.di

import com.fcorallini.authentication_data.matcher.EmailMatcherImpl
import com.fcorallini.authentication_data.repository.AuthenticationRepositoryImpl
import com.fcorallini.authentication_domain.matcher.EmailMatcher
import com.fcorallini.authentication_domain.repository.AuthenticationRepository
import com.fcorallini.authentication_domain.usecases.GetUserIdUseCase
import com.fcorallini.authentication_domain.usecases.LoginUseCase
import com.fcorallini.authentication_domain.usecases.LogoutUseCase
import com.fcorallini.authentication_domain.usecases.SignupUseCase
import com.fcorallini.authentication_domain.usecases.ValidateEmailUseCase
import com.fcorallini.authentication_domain.usecases.ValidatePasswordUseCase
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

    @Provides
    @Singleton
    fun providesGetUserIdUseCase(authenticationRepository: AuthenticationRepository): GetUserIdUseCase {
        return GetUserIdUseCase(authenticationRepository)
    }

    @Provides
    @Singleton
    fun providesLoginUseCase(authenticationRepository: AuthenticationRepository): LoginUseCase {
        return LoginUseCase(authenticationRepository)
    }

    @Provides
    @Singleton
    fun providesLogoutUseCase(authenticationRepository: AuthenticationRepository): LogoutUseCase {
        return LogoutUseCase(authenticationRepository)
    }

    @Provides
    @Singleton
    fun providesSignupUseCase(authenticationRepository: AuthenticationRepository): SignupUseCase {
        return SignupUseCase(authenticationRepository)
    }

    @Provides
    @Singleton
    fun providesValidateEmailUseCase(emailMatcher: EmailMatcher) : ValidateEmailUseCase {
        return ValidateEmailUseCase(emailMatcher)
    }

    @Provides
    @Singleton
    fun providesValidatePasswordUseCase() : ValidatePasswordUseCase {
        return ValidatePasswordUseCase()
    }
}