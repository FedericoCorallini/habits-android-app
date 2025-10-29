package com.fcorallini.habits.authentication.data.repository

import com.fcorallini.habits.authentication.domain.repository.AuthenticationRepository

class FakeAuthenticationRepository : AuthenticationRepository {

    var fakeError = false

    override suspend fun login(email: String, password: String): Result<Unit> {
        return if (fakeError) {
            Result.failure(Exception())
        } else Result.success(Unit)
    }

    override suspend fun signup(email: String, password: String): Result<Unit> {
        return if (fakeError) {
            Result.failure(Exception())
        } else Result.success(Unit)
    }

    override fun getUserId(): String {
        return "1"
    }

    override suspend fun logout() {
    }

}