package com.fcorallini.habits.authentication.presentation.login

import com.fcorallini.authentication_data.matcher.EmailMatcherImpl
import com.fcorallini.authentication_data.repository.AuthenticationRepositoryImpl
import com.fcorallini.habits.authentication.data.repository.FakeAuthenticationRepository
import com.fcorallini.authentication_domain.matcher.EmailMatcher
import com.fcorallini.authentication_domain.usecases.LoginUseCase
import com.fcorallini.authentication_domain.usecases.ValidateEmailUseCase
import com.fcorallini.authentication_domain.usecases.ValidatePasswordUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class LoginViewModelTest {

    private lateinit var loginViewModel: com.fcorallini.authentication_presentation.login.LoginViewModel
    private lateinit var authenticationRepository: FakeAuthenticationRepository

    private var dispatcher = StandardTestDispatcher()
    private var scope = TestScope(dispatcher)

    @Before
    fun setUp() {
        authenticationRepository = FakeAuthenticationRepository()
        val loginUseCase =
            com.fcorallini.authentication_domain.usecases.LoginUseCase(authenticationRepository)
        val validatePasswordUseCase =
            com.fcorallini.authentication_domain.usecases.ValidatePasswordUseCase()
        val validateEmailUseCase =
            com.fcorallini.authentication_domain.usecases.ValidateEmailUseCase(
                object : com.fcorallini.authentication_domain.matcher.EmailMatcher {
                    override fun isValid(email: String): Boolean {
                        return email.isNotEmpty()
                    }
                }
            )
        loginViewModel = com.fcorallini.authentication_presentation.login.LoginViewModel(
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
            com.fcorallini.authentication_presentation.login.LoginState(
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
        loginViewModel.onEvent(com.fcorallini.authentication_presentation.login.LoginEvent.EmailChanged(""))
        loginViewModel.onEvent(com.fcorallini.authentication_presentation.login.LoginEvent.Login)
        assertNotNull(loginViewModel.state.emailError)
    }

    @Test
    fun `given an email, validate that the email state changes`() {
        var state = loginViewModel.state
        assertEquals(state.email, "")
        loginViewModel.onEvent(com.fcorallini.authentication_presentation.login.LoginEvent.EmailChanged("email@example.com"))
        state = loginViewModel.state
        assertEquals(state.email, "email@example.com")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `set valid details, Login, starts loading, then log in`() = scope.runTest {
        loginViewModel.onEvent(com.fcorallini.authentication_presentation.login.LoginEvent.EmailChanged("email@example.com"))
        loginViewModel.onEvent(com.fcorallini.authentication_presentation.login.LoginEvent.PasswordChanged("Pass1234"))
        loginViewModel.onEvent(com.fcorallini.authentication_presentation.login.LoginEvent.Login)
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
        loginViewModel.onEvent(com.fcorallini.authentication_presentation.login.LoginEvent.EmailChanged("email@example.com"))
        loginViewModel.onEvent(com.fcorallini.authentication_presentation.login.LoginEvent.PasswordChanged("Pass1234"))
        loginViewModel.onEvent(com.fcorallini.authentication_presentation.login.LoginEvent.Login)
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