package com.fcorallini.habits.authentication.domain.usecases

import com.fcorallini.habits.authentication.domain.matcher.EmailMatcher
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor(
    private val emailMatcher: EmailMatcher
) {
    operator fun invoke(email : String) : Boolean {
        return emailMatcher.isValid(email)
    }
}