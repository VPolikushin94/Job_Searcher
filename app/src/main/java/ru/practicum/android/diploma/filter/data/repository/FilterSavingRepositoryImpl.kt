package ru.practicum.android.diploma.filter.data.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.filter.domain.api.FilterSavingRepository
import ru.practicum.android.diploma.filter.domain.models.FiltrationSettings
import ru.practicum.android.diploma.filter.domain.models.Industry

class FilterSavingRepositoryImpl(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences,
) : FilterSavingRepository {

    private var cachedFiltrationSettings = getFilters()

    override fun setIndustries(industry: Industry?) {
        val industryJson = gson.toJson(industry)
        sharedPreferences.edit().putString(INDUSTRY_SETTINGS, industryJson).apply()
    }

    override fun getSavedIndustries(): Industry? {
        val industryJson = sharedPreferences.getString(INDUSTRY_SETTINGS, null)
        return gson.fromJson(industryJson, Industry::class.java)
    }

    override fun setSalary(salary: String) {
        sharedPreferences.edit()
            .putString(SALARY_SETTINGS, salary)
            .apply()
    }

    override fun getSalary(): String {
        return sharedPreferences.getString(SALARY_SETTINGS, "") ?: ""
    }

    override fun setSalaryOnly(isChecked: Boolean) {
        sharedPreferences.edit().putBoolean(SALARY_ONLY_SETTINGS, isChecked).apply()
    }

    override fun getSalaryOnly(): Boolean {
        return sharedPreferences.getBoolean(SALARY_ONLY_SETTINGS, false)
    }

    override fun allDelete() {
        sharedPreferences.edit().clear().apply()
    }

    override fun getFilters(): FiltrationSettings {
        val industry = getSavedIndustries()
        val salary = getSalary()
        val salaryOnly = getSalaryOnly()
        return FiltrationSettings(
            industry,
            salary,
            salaryOnly
        )
    }

    override suspend fun areFiltersChanged(): Boolean {
        val currentFiltrationSettings = getFilters()
        return if (currentFiltrationSettings != cachedFiltrationSettings) {
            cachedFiltrationSettings = currentFiltrationSettings
            true
        } else {
            false
        }
    }

    override suspend fun areFiltersEmpty(): Boolean {
        val currentFiltrationSettings = getFilters()
        return currentFiltrationSettings == FiltrationSettings(null, "", false)
    }

    companion object {
        private const val INDUSTRY_SETTINGS = "INDUSTRY_SETTINGS"
        private const val SALARY_SETTINGS = "SALARY_SETTINGS"
        private const val SALARY_ONLY_SETTINGS = "SALARY_ONLY_SETTINGS"
    }
}
