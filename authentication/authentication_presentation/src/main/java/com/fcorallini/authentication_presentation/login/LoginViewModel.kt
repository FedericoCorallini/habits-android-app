package com.fcorallini.authentication_presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fcorallini.authentication_domain.usecases.LoginUseCase
import com.fcorallini.authentication_domain.usecases.PasswordResult
import com.fcorallini.authentication_domain.usecases.ValidateEmailUseCase
import com.fcorallini.authentication_domain.usecases.ValidatePasswordUseCase
import com.fcorallini.core_data.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
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

        if (!validateEmailUseCase(state.email)) {
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
            viewModelScope.launch(dispatcher) {
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