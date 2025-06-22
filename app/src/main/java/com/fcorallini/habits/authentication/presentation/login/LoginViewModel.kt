package com.fcorallini.habits.authentication.presentation.login

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fcorallini.habits.authentication.domain.usecases.LoginUseCase
import com.fcorallini.habits.authentication.domain.usecases.PasswordResult
import com.fcorallini.habits.authentication.domain.usecases.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.EmailChanged -> {
                state = state.copy(
                    email = event.email
                )
            }
            LoginEvent.Login -> {
                login()
            }
            is LoginEvent.PasswordChanged -> {
                state = state.copy(
                    password = event.password
                )
            }
        }
    }

    private fun login() {
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
        if (passwordResult is PasswordResult.Invalid) {
            state = state.copy(
                passwordError = passwordResult.error
            )
        }

        if(state.passwordError == null && state.emailError == null){
            state = state.copy(isLoading = true)
            viewModelScope.launch {
                loginUseCase(state.email, state.password).onSuccess {
                    state = state.copy(isLoggedIn = true)
                }.onFailure {
                    val error = it.message
                    println(error)
                }
                state = state.copy(isLoading = false)
            }
        }
    }
}