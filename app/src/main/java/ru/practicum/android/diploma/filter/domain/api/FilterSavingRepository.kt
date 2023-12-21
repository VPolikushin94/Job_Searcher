package ru.practicum.android.diploma.filter.domain.api

import android.annotation.SuppressLint
import ru.practicum.android.diploma.filter.domain.models.FiltrationSettings
import ru.practicum.android.diploma.filter.domain.models.Industry

@Suppress("TooManyFunctions")
interface FilterSavingRepository {

    fun setIndustries(industry: Industry?)

    fun getSavedIndustries(): Industry?

    fun removeIndustries()

    fun setSalary(salary: String)

    fun getSalary(): String

    fun setSalaryOnly(isChecked: Boolean)

    fun getSalaryOnly(): Boolean

    fun allDelete()

    fun getFiltrationSettings(): FiltrationSettings

    suspend fun areFiltersChanged(): Boolean

    suspend fun areFiltersEmpty(): Boolean
}
