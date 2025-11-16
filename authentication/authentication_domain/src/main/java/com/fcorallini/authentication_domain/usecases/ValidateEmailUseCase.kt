package com.fcorallini.authentication_domain.usecases

import com.fcorallini.authentication_domain.matcher.EmailMatcher

class ValidateEmailUseCase(
    private val emailMatcher: EmailMatcher
) {
    operator fun invoke(email : String) : Boolean {
        return emailMatcher.isValid(email)
    }
}