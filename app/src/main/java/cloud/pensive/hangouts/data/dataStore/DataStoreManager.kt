package cloud.pensive.hangouts.data.dataStore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "hangouts_datastore")


class DataStoreManager(private val context: Context) {
    companion object {
        val ONBOARDING_KEY = booleanPreferencesKey("onboarding_completed")
    }

    val onboardingCompleted: Flow<Boolean> =
        context.dataStore.data.map { preferences ->
            preferences[ONBOARDING_KEY] ?: false
        }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[ONBOARDING_KEY] = completed
        }
    }


}