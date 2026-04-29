package com.example.systemassesment.data.repository

import com.example.systemassesment.data.local.datastore.SessionPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val sessionPreferences: SessionPreferences
) : AuthRepository {
    override fun observeIsLoggedIn(): Flow<Boolean> = sessionPreferences.isLoggedIn

    override suspend fun setLoggedIn(loggedIn: Boolean) {
        sessionPreferences.setLoggedIn(loggedIn)
    }
}
