package com.fcorallini.habits.authentication.domain.usecases

import com.fcorallini.habits.authentication.domain.repository.AuthenticationRepository
import javax.inject.Inject

class GetUserIdUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke() : String? {
        return authenticationRepository.getUserId()
    }
}