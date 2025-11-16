package com.fcorallini.habits.authentication.data.repository

import com.fcorallini.authentication_domain.repository.AuthenticationRepository

class FakeAuthenticationRepository :
    com.fcorallini.authentication_domain.repository.AuthenticationRepository {

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