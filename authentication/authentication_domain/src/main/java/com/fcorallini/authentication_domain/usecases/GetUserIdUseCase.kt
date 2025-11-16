package com.fcorallini.authentication_domain.usecases

import com.fcorallini.authentication_domain.repository.AuthenticationRepository

class GetUserIdUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke() : String? {
        return authenticationRepository.getUserId()
    }
}