package com.mhmdnurulkarim.hukumq.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.mhmdnurulkarim.hukumq.utils.Const.DATA_STORE_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

class ThemeDataStore private constructor(private val dataStore: DataStore<Preferences>) {

    fun getThemeSetting(): Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }

    suspend fun saveThemeSetting(isDarkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkMode
        }
    }

    companion object {
        @Volatile
        private var mInstance: ThemeDataStore? = null
        private val THEME_KEY = booleanPreferencesKey("THEME_PREF_KEY")

        fun getInstance(dataStore: DataStore<Preferences>): ThemeDataStore =
            mInstance ?: synchronized(this) {
                val newInstance = mInstance ?: ThemeDataStore(dataStore)
                    .also { mInstance = it }
                newInstance
            }
    }
}