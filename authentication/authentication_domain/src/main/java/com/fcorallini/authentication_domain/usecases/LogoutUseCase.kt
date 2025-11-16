package com.fcorallini.authentication_domain.usecases

import com.fcorallini.authentication_domain.repository.AuthenticationRepository

class LogoutUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke() {
        authenticationRepository.logout()
    }
}