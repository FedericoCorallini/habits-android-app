package com.fcorallini.habits.authentication.data.repository

import com.fcorallini.habits.authentication.domain.repository.AuthenticationRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

class AuthenticationRepositoryImpl : AuthenticationRepository {

    override suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e : Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signup(email: String, password: String): Result<Unit> {
        return try {
            Firebase.auth.createUserWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e : Exception) {
            Result.failure(e)
        }
    }

    override fun getUserId(): String? {
        return Firebase.auth.currentUser?.uid
    }

    override suspend fun logout() {
        Firebase.auth.signOut()
    }
}