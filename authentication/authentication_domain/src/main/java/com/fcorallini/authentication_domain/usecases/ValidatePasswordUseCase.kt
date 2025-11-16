package com.fcorallini.authentication_domain.usecases

class ValidatePasswordUseCase(){
    operator fun invoke(password : String) : PasswordResult {
        return when {
            password.length < 6 -> {
                PasswordResult.Invalid("Password must have at least 6 characters")
            }
            !password.any{ it.isLowerCase() } -> {
                PasswordResult.Invalid("Password must have at least 1 lowercase")
            }
            !password.any{ it.isUpperCase() } -> {
                PasswordResult.Invalid("Password must have at least 1 uppercase")
            }
            !password.any{ it.isDigit() } -> {
                PasswordResult.Invalid("Password must have at least 1 digit")
            }
            else -> {
                PasswordResult.Valid
            }
        }
    }
}

sealed class PasswordResult {
    data object Valid : PasswordResult()
    data class Invalid(val error : String) : PasswordResult()
}