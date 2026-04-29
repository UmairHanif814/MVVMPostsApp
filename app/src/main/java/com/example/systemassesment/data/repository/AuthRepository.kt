package com.example.systemassesment.data.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun observeIsLoggedIn(): Flow<Boolean>
    suspend fun setLoggedIn(loggedIn: Boolean)
}
