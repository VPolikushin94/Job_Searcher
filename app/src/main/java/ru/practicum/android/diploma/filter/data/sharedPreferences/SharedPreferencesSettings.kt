package ru.practicum.android.diploma.filter.data.sharedPreferences

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.filter.domain.models.FiltrationSettings
import java.lang.reflect.Type

class SharedPreferencesSettings(
    private val sharedPreferences: SharedPreferences, private val gson: Gson
) {

    fun createFiltrationSettingsFromJson(json: String): FiltrationSettings {
        val typeOfTrackList: Type = object : TypeToken<FiltrationSettings?>() {}.type
        return gson.fromJson(json, typeOfTrackList)
    }

    fun createJsonFromFiltrationSettings(filtrationSettings: FiltrationSettings): String {
        return gson.toJson(filtrationSettings)
    }

    fun getFiltrationSettingsFromJson(): FiltrationSettings? {
        val json: String? = sharedPreferences.getString(FILTRATION_SETTINGS, null)
        return if (json.isNullOrEmpty()) null else createFiltrationSettingsFromJson(json)
    }

    companion object {
        private const val FILTRATION_SETTINGS = "filtration_settings"
    }
}
