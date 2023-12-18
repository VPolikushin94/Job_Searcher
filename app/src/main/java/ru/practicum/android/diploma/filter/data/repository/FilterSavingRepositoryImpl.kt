package ru.practicum.android.diploma.filter.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import ru.practicum.android.diploma.filter.data.sharedPreferences.SharedPreferencesSettings
import ru.practicum.android.diploma.filter.domain.api.FilterSavingRepository
import ru.practicum.android.diploma.filter.domain.models.FiltrationSettings

class FilterSavingRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val sharedPreferencesSettings: SharedPreferencesSettings,
) : FilterSavingRepository {
    override fun getFiltrationSettings(): FiltrationSettings? {
        return sharedPreferencesSettings.getFiltrationSettingsFromJson()
    }

    override fun saveFiltrationSettings(filtrationSettings: FiltrationSettings) {
        sharedPreferences.edit()
            .putString(
                FILTRATION_SETTINGS,
                sharedPreferencesSettings.createJsonFromFiltrationSettings(filtrationSettings)
            )
            .apply()
    }

    override fun removeFiltrationSettings() {
        sharedPreferences.edit {
            remove(FILTRATION_SETTINGS)
        }
    }

    companion object {
        private const val FILTRATION_SETTINGS = "filtration_settings"
    }
}
