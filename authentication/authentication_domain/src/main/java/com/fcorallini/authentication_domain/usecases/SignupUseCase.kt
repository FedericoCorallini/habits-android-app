package com.fcorallini.authentication_domain.usecases

import com.fcorallini.authentication_domain.repository.AuthenticationRepository

class SignupUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(email : String, password : String) : Result<Unit> {
        return authenticationRepository.signup(email, password)
    }
}