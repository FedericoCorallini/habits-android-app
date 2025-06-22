package com.fcorallini.habits.authentication.presentation.signup

sealed interface SignupEvent {
    data class EmailChanged(val email : String) : SignupEvent
    data class PasswordChanged(val password : String) : SignupEvent
    data object LogIn : SignupEvent
    data object SignUp : SignupEvent
}