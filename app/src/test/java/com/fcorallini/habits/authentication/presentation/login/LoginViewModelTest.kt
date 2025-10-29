package com.fcorallini.habits.authentication.presentation.login

import com.fcorallini.habits.authentication.data.matcher.EmailMatcherImpl
import com.fcorallini.habits.authentication.data.repository.AuthenticationRepositoryImpl
import com.fcorallini.habits.authentication.data.repository.FakeAuthenticationRepository
import com.fcorallini.habits.authentication.domain.matcher.EmailMatcher
import com.fcorallini.habits.authentication.domain.usecases.LoginUseCase
import com.fcorallini.habits.authentication.domain.usecases.ValidateEmailUseCase
import com.fcorallini.habits.authentication.domain.usecases.ValidatePasswordUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class LoginViewModelTest {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var authenticationRepository: FakeAuthenticationRepository

    private var dispatcher = StandardTestDispatcher()
    private var scope = TestScope(dispatcher)

    @Before
    fun setUp() {
        authenticationRepository = FakeAuthenticationRepository()
        val loginUseCase = LoginUseCase(authenticationRepository)
        val validatePasswordUseCase = ValidatePasswordUseCase()
        val validateEmailUseCase = ValidateEmailUseCase(
            object : EmailMatcher {
                override fun isValid(email: String): Boolean {
                    return email.isNotEmpty()
                }
            }
        )
        loginViewModel = LoginViewModel(
            loginUseCase,
            validatePasswordUseCase,
            validateEmailUseCase,
            dispatcher
        )
    }

    @Test
    fun `initial state is empty`() {
        val state = loginViewModel.state

        assertEquals(
            LoginState(
                email = "",
                password = "",
                emailError = null,
                passwordError = null,
                signUp = false,
                isLoggedIn = false,
                isLoading = false
            ),
            state
        )
    }

    @Test
    fun `given invalid email, show email error`() {
        loginViewModel.onEvent(LoginEvent.EmailChanged(""))
        loginViewModel.onEvent(LoginEvent.Login)
        assertNotNull(loginViewModel.state.emailError)
    }

    @Test
    fun `given an email, validate that the email state changes`() {
        var state = loginViewModel.state
        assertEquals(state.email, "")
        loginViewModel.onEvent(LoginEvent.EmailChanged("email@example.com"))
        state = loginViewModel.state
        assertEquals(state.email, "email@example.com")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `set valid details, Login, starts loading, then log in`() = scope.runTest {
        loginViewModel.onEvent(LoginEvent.EmailChanged("email@example.com"))
        loginViewModel.onEvent(LoginEvent.PasswordChanged("Pass1234"))
        loginViewModel.onEvent(LoginEvent.Login)
        var state = loginViewModel.state
        assertNull(state.passwordError)
        assertNull(state.emailError)
        assert(state.isLoading)
        advanceUntilIdle()
        state = loginViewModel.state
        assert(state.isLoggedIn)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `set valid details but server error, Login, starts loading, then log in`() = scope.runTest {
        authenticationRepository.fakeError = true
        loginViewModel.onEvent(LoginEvent.EmailChanged("email@example.com"))
        loginViewModel.onEvent(LoginEvent.PasswordChanged("Pass1234"))
        loginViewModel.onEvent(LoginEvent.Login)
        var state = loginViewModel.state
        assertNull(state.passwordError)
        assertNull(state.emailError)
        assert(state.isLoading)
        advanceUntilIdle()
        state = loginViewModel.state
        assert(!state.isLoggedIn)
        assert(!state.isLoading)
    }
}