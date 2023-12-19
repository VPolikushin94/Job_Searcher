package ru.practicum.android.diploma.filter.domain.api

import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.vacancy.data.details.Salary

interface FilterSavingRepository {

    fun setIndustries(industry: Industry?)

    fun getSavedIndustries(): Industry?

    fun setSalary(salary: String)

    fun getSalary(): String

    fun setSalaryOnly(isChecked: Boolean)

    fun getSalaryOnly(): Boolean
}
