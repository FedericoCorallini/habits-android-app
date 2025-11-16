package com.fcorallini.authentication_presentation.signup

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fcorallini.authentication_domain.usecases.SignupUseCase
import com.fcorallini.authentication_domain.usecases.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val signupUseCase: SignupUseCase
) : ViewModel() {

    var state by mutableStateOf(SignupState())
        private set

    fun onEvent( event: SignupEvent) {
        when(event) {
            is SignupEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is SignupEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            SignupEvent.SignUp -> {
                signUp()
            }
            SignupEvent.LogIn -> {
                state = state.copy(logIn = true)
            }
        }
    }

    private fun signUp() {
        state = state.copy(
            emailError = null,
            passwordError = null
        )

        if (!Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) {
            state = state.copy(
                emailError = "invalid email"
            )
        }

        val passwordResult = validatePasswordUseCase(state.password)
        if (passwordResult is com.fcorallini.authentication_domain.usecases.PasswordResult.Invalid) {
            state = state.copy(
                passwordError = passwordResult.error
            )
        }

        if(state.passwordError == null && state.emailError == null){
            state = state.copy(isLoading = true)
            viewModelScope.launch {
                signupUseCase(state.email, state.password).onSuccess {
                    state = state.copy(isSignedIn = true)
                }.onFailure {
                    val error = it.message
                    println(error)
                }
                state = state.copy(isLoading = false)
            }
        }
    }
}