package com.teguh.githubuserfinal.setting

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val PREFERENCES_NAME = "settings"

val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
    name = PREFERENCES_NAME
)

class SettingsDataStore(preference_datastore: DataStore<Preferences>) {
    private val THEME_KEY = booleanPreferencesKey("theme_setting")

    val getThemeSetting: Flow<Boolean> = preference_datastore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            // On the first run of the app, we will use LinearLayoutManager by default
            preferences[THEME_KEY] ?: false
        }

    suspend fun saveThemeSetting(isLightModeActive: Boolean, context: Context) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = isLightModeActive
        }
    }
}