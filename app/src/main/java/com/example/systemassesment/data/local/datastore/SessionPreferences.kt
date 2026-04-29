package com.example.systemassesment.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val loggedInKey = booleanPreferencesKey("is_logged_in")

    val isLoggedIn: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[loggedInKey] ?: false
    }

    suspend fun setLoggedIn(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[loggedInKey] = value
        }
    }
}
