package com.fcorallini.habits.authentication.domain.usecases

import com.fcorallini.habits.authentication.domain.repository.AuthenticationRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke() {
        authenticationRepository.logout()
    }
}