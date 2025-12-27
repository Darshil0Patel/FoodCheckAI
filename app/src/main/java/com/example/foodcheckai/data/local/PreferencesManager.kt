package com.example.foodcheckai.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "food_check_preferences")

class PreferencesManager(private val context: Context) {

    companion object {
        private val SCAN_COUNT_KEY = intPreferencesKey("scan_count")

        @Volatile
        private var INSTANCE: PreferencesManager? = null

        fun getInstance(context: Context): PreferencesManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: PreferencesManager(context.applicationContext).also { INSTANCE = it }
            }
        }
    }

    val scanCountFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[SCAN_COUNT_KEY] ?: 0
    }

    suspend fun incrementScanCount() {
        context.dataStore.edit { preferences ->
            val currentCount = preferences[SCAN_COUNT_KEY] ?: 0
            preferences[SCAN_COUNT_KEY] = currentCount + 1
        }
    }

    suspend fun getScanCount(): Int {
        var count = 0
        context.dataStore.data.collect { preferences ->
            count = preferences[SCAN_COUNT_KEY] ?: 0
        }
        return count
    }

    suspend fun resetScanCount() {
        context.dataStore.edit { preferences ->
            preferences[SCAN_COUNT_KEY] = 0
        }
    }
}
