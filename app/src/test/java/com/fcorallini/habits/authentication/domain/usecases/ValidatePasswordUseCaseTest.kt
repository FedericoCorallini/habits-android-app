package com.fcorallini.habits.authentication.domain.usecases

import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class ValidatePasswordUseCaseTest {

    lateinit var validatePasswordUseCase : ValidatePasswordUseCase

    @Before
    fun setup() {
        validatePasswordUseCase = ValidatePasswordUseCase()
    }

    @Test
    fun `given low character password, return invalid password`() {
        val input = "asd"
        val result = validatePasswordUseCase(input)

        assertEquals(PasswordResult.Invalid("Password must have at least 6 characters"), result)
    }

    @Test
    fun `given no lowercase password, return invalid password`() {
        val input = "ASDASDASD"
        val result = validatePasswordUseCase(input)

        assertEquals(PasswordResult.Invalid("Password must have at least 1 lowercase"), result)
    }

    @Test
    fun `given no uppercase password, return invalid password`() {
        val input = "asdasdas"
        val result = validatePasswordUseCase(input)

        assertEquals(PasswordResult.Invalid("Password must have at least 1 uppercase"), result)
    }

    @Test
    fun `given no number password, return invalid password`() {
        val input = "asdASDasd"
        val result = validatePasswordUseCase(input)

        assertEquals(PasswordResult.Invalid("Password must have at least 1 digit"), result)
    }

    @Test
    fun `given invalid password, return invalid password`() {
        val input = "asdASDasd"
        val result = validatePasswordUseCase(input)

        assertEquals(PasswordResult.Invalid("").javaClass, result.javaClass)
    }
}