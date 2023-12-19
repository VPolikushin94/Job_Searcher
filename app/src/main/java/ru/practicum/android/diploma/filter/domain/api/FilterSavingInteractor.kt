package ru.practicum.android.diploma.filter.domain.api

import ru.practicum.android.diploma.filter.domain.models.FiltrationSettings
import ru.practicum.android.diploma.filter.domain.models.Industry

interface FilterSavingInteractor {

    fun getFilters(): FiltrationSettings

    fun setIndustries(industry: Industry?)

    fun getSavedIndustry(): Industry?

    fun setSalary(salary: String)

    fun getSalary(): String

    fun setSalaryOnly(isChecked: Boolean)

    fun getSalaryOnly(): Boolean

    fun allDelete()
}
