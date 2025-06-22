package com.fcorallini.habits.authentication.domain.usecases

import com.fcorallini.habits.authentication.domain.repository.AuthenticationRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(email : String, password : String) : Result<Unit> {
        return authenticationRepository.login(email, password)
    }
}